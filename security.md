# MinSuke — Security

**Status:** Loop 02 — Updated  
**Date:** 2026-08-11  
**Version:** 0.2

> 本書は方針・リスク整理であり、最終セキュリティポリシーは人間承認が必要。

---

## 1. Security Principles

| 原則 | 区分 |
|---|---|
| セキュリティを後付けにしない | Confirmed |
| 個人情報を扱う前提で設計する | Proposed |
| 最小権限の原則 | Proposed |
| 機密設定をリポジトリに含めない | Confirmed（旧 MinSuke 実績） |

---

## 2. Threat Model（概要）

| 脅威 | 影響 | 対策方向 |
|---|---|---|
| 認証情報の漏洩 | アカウント乗っ取り | パスワードハッシュ、HTTPS |
| 水平権限昇格 | 他家庭データ閲覧・改ざん |  household_id によるアクセス制御 |
| 垂直権限昇格 | 管理者機能の不正利用 | ロールベース認可 |
| CSRF | 意図しない操作 | CSRF トークン |
| XSS | スクリプト注入 | 出力エスケープ、CSP（将来） |
| SQL インジェクション | DB 不正操作 | JPA パラメータバインド |
| セッションハイジャック | なりすまし | Secure Cookie、セッションタイムアウト |

---

## 3. User Roles（Proposed）

| ロール | 説明 | MVP |
|---|---|---|
| **ADMIN** | 全機能・全データ管理、**イベント作成** | **Confirmed** |
| **PARENT** | 自家庭の CRUD、**本人・子ども個別**のイベント参加 | Yes |
| **INSTRUCTOR** | 担当スケジュール参照・更新 | No（Loop 08 では導入しない — OQ-I01） |

### Permission Matrix（MVP + Loop 08 案）

| リソース | ADMIN | PARENT | INSTRUCTOR |
|---|---|---|---|
| 自家庭情報 | CRUD | CRUD | — |
| 他家庭情報 | R | R（一覧・詳細） | — |
| イベント作成 | **CRUD（ADMIN のみ）** | — | — |
| イベント参加 | R（全参加者一覧） | CRU（**自家庭の保護者・子ども個別**） | — |
| 講師マスタ | **CRUD** | R（有効のみ） | — |
| イベント担当講師の設定 | **CU** | R | — |
| 講師稼働（担当イベント） | R | R | — |
| ユーザー管理 | CRUD | — | — |

### Loop 08 認可方針（Proposed）

| 操作 | ロール | 備考 |
|---|---|---|
| 講師一覧・詳細 | 認証済み全員 | **OQ-I02 ✅** |
| 作成・編集・削除・無効化 | **ADMIN のみ** | Service + SecurityConfig |
| 講師ログイン | 導入しない（Loop 08） | **OQ-I01 ✅** |

### Confirmed（2026-08-10）

- MVP に **ADMIN ロールを含む**
- **イベント作成は ADMIN のみ**
- 参加登録は **保護者・子ども個別**（PARENT が自家庭メンバーを代理登録）

---

## 4. Authentication Strategy

### Proposed Options

| 方式 | メリット | デメリット | MVP 評価 |
|---|---|---|---|
| **セッション + Cookie** | シンプル、Thymeleaf と相性良 | スケール時 Sticky Session | **第一候補** |
| JWT（Stateless） | API 向き | SSR ではやや複雑 | 将来 |
| 外部 IdP（OAuth2） | パスワード管理不要 | 構成・依存増 | Future |

### Password Management（Proposed）

| 項目 | 方針 |
|---|---|
| 保存 | BCrypt 等の適応的ハッシュ（平文禁止） |
| ポリシー | Open Question（最小長・複雑さ） |
| リセット | MVP 外または簡易版を検討 |

### Session Management（Proposed）

| 項目 | 方針 |
|---|---|
| タイムアウト | 30 分無操作（案） |
| Cookie | `HttpOnly`, `Secure`（本番）, `SameSite=Lax` |
| ログアウト | セッション無効化 |

---

## 5. Authorization Strategy

### Proposed

1. **認証:** ログイン済み User を Security Context / Session に保持
2. **認可:** ロール + リソース所有者（`household_id`）で判定
3. **実装方針（Loop 02 確定）:**
   - **Spring Security** を採用（旧 MinSuke の Interceptor 方式は採用しない）
   - **Loop 04:** 依存追加 + `permitAll` の最小 `SecurityFilterChain`
   - **Loop 05:** フォームログイン、ロール認可、CSRF、セッション管理を本格実装

### Access Control Rules（Proposed）

```
保護者は household_id が一致するデータのみ参照・更新可能
管理者は全 household を参照可能
イベント作成は ADMIN ロールのみ
イベント参加は自 household の保護者・子ども個別に登録可能
定員は参加者 1 名 = 1 カウント
```

---

## 6. Input Validation

| レイヤー | 手段 |
|---|---|
| Controller | `@Valid` + Bean Validation |
| Service | 業務ルール（定員、重複参加） |
| DB | NOT NULL, UNIQUE, FK |

---

## 7. CSRF Protection

| 項目 | 方針 |
|---|---|
| MVP | Spring Security 標準（Loop 05 から有効） |

---

## 8. Sensitive Data

| データ | 分類 | 方針 |
|---|---|---|
| パスワード | 機密 | ハッシュのみ保存 |
| メールアドレス | 個人関連 | アクセス制御 |
| 氏名・電話 | 個人情報 | ロール + 所有者チェック |
| 設定ファイル | 機密 | `.gitignore`、環境変数 |

### 保持期間・削除ポリシー（Confirmed — 2026-08-11）

**選択肢 B（標準保持）** — 詳細は `requirements.md` §7.1

| データ | 保持期間 | 削除方法 |
|---|---|---|
| アカウント・家族情報 | 利用中 + 退会後 30 日以内 | 物理削除 |
| 参加記録 | イベント終了後 3 年 | 匿名化 |
| ログ | 1 年 | 自動削除 |
| バックアップ | 作成後 90 日 | ローテーション |

---

## 9. Logging & Audit

| 種別 | MVP | 将来 |
|---|---|---|
| アクセスログ | 基本（URL, user_id） | 詳細 |
| 認証ログ | ログイン成功/失敗 | 異常検知 |
| 監査証跡 | No | データ変更履歴テーブル |
| パスワード | ログ出力禁止 | — |

---

## 10. Backup & Recovery

| 項目 | 状態 |
|---|---|
| バックアップ頻度 | Open Question |
| リストア手順 | Open Question |
| RPO / RTO | Open Question |

---

## 11. Security Decisions

### SD-01: パスワードハッシュ化（Proposed）

| 項目 | 内容 |
|---|---|
| Decision | BCrypt で password_hash を保存 |
| Reason | 業界標準、Spring Security 標準 |
| Approved | **Yes**（2026-08-11 — Loop 05） |
| 実装 | `BCryptPasswordEncoder`、`users.password_hash` |

### SD-02: Spring Security 導入タイミング

| 項目 | 内容 |
|---|---|
| Decision | Loop 04 で依存追加、Loop 05 で本格実装 |
| Reason | セキュリティ by Design。Interceptor 移行コストを回避 |
| Alternative | Loop 04 から本格導入 / Interceptor のみ |
| Why rejected | Loop 04 は基盤構築に集中。Interceptor は旧方式で不採用 |
| Approved | **Yes**（2026-08-11 — Loop 02） |

### SD-03: 初回 ADMIN 作成

| 項目 | 内容 |
|---|---|
| Decision | 公開登録は PARENT のみ。ADMIN は Flyway seed（dev/local） |
| Reason | 権限昇格の脆弱性を防止 |
| Approved | **Yes**（2026-08-11 — Loop 02） |

---

## 12. Open Questions

- パスワードポリシー（最小 8 文字等）
- ログイン試行回数制限・ロックアウト
- 個人情報保護法上の利用目的・同意取得
- 本番 HTTPS 証明書・運用
- セキュリティテストの自動化範囲

---

## 13. Related Documents

- `requirements.md` — NFR-01 個人情報
- `architecture.md` — レイヤー構成
- `database.md` — users テーブル
