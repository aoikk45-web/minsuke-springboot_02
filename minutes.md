# MinSuke Development Minutes

## Project

**MinSuke（みんスケ）**

## Development Model

**Greenfield / New Development**

## Development Environment

- Cursor
- Composer 2.5

## Current Loop

**MVP（Loop 04〜07）完了** — 統合レビュー済（2026-08-11）  
**Loop 08 / 09 / 10 / 11 / 12 / 13 / 14 / 15 / 16 / 17 完了** — PR #1 / #2 / #4 / #6 / #7 / #8 / #9 / **#10** / **#12** を `main` へ merge 済（Loop 17 は PR 作成）  
**Current Loop:** Loop 17 完了 — 次 Loop は人間承認待ち

## Date

**2026-08-17**（最終更新）

---

# 1. Project Status

## Current State

**Loop 17 完了**（2026-08-17 人間 UI 確認）。PR は `feature/loop-17-admin-participation`。  
**次 Loop:** 人間承認待ち（Testing/CI、または OQ-P01〜P05）。  
**将来（未着手）:** 個人情報・メール・サブスク決済を外部システムへ寄せる案（`minutes.md` §33 / OQ-P01〜P05）。

## Loop 16 Progress

| 作業 | 状態 |
|---|---|
| 人間承認（OQ-E02〜E05 / 案 A） | ✅ **2026-08-15** |
| ブランチ `feature/loop-16-participation-history` | ✅ |
| `GET /my-participations` + S24 + ナビ | ✅ |
| EventServiceTest / EventControllerSecurityTest | ✅ |
| Consistency Review（`roles.md` §12） | ✅ **2026-08-16**（`minutes.md` §32.1） |
| UI ローカル確認 | ✅ **2026-08-16**（人間確認） |
| Loop 16 完了（PR #12 / merge） | ✅ **2026-08-16** |

## Loop 17 Progress

| 作業 | 状態 |
|---|---|
| 設計草案（案 A＋家庭参加率） | ✅ **2026-08-16** |
| 人間承認（OQ-R01〜R06） | ✅ **2026-08-17** |
| ブランチ `feature/loop-17-admin-participation` | ✅ |
| `GET /schedules/{id}/participations` + `/admin/participations` | ✅ |
| ローカルシード入れ替え（V10） | ✅ |
| テスト | ✅ |
| Consistency Review（`roles.md` §12） | ✅ **2026-08-17**（`minutes.md` §34.1） |
| UI ローカル確認 | ✅ **2026-08-17**（人間確認） |
| Loop 17 完了 | ✅ **2026-08-17** |

## Loop 12 Progress

| 作業 | 状態 |
|---|---|
| ブランチ作成 `feature/loop-12-participation-unit` | ✅ |
| FR-S03 / OQ-S02 設計草案 | ✅ |
| 人間承認（推奨案） | ✅ **2026-08-14** |
| Flyway V9 + 参加登録 UI | ✅ |
| テスト（Testcontainers） | ⏭ Docker Desktop 未起動のためスキップ |
| Consistency Review（`roles.md` §12） | ✅ **2026-08-14** |
| UI ローカル確認 | ✅ **2026-08-14**（人間確認） |
| Loop 12 完了（PR #7 / merge） | ✅ **2026-08-14** |

## Loop 13 Progress

| 作業 | 状態 |
|---|---|
| ブランチ作成 `feature/loop-13-my-participation` | ✅ |
| 人間承認（OQ-E01 / DD-22） | ✅ **2026-08-14** |
| カレンダー色分け + 本日参加 | ✅ |
| テスト（Testcontainers） | ⏭ Docker Desktop 未起動のためスキップ |
| Consistency Review（`roles.md` §12） | ✅ **2026-08-14** |
| UI ローカル確認 | ✅ **2026-08-14**（人間確認） |
| Loop 13 完了（PR #8 / merge） | ✅ **2026-08-14** |

## Loop 14 Progress

| 作業 | 状態 |
|---|---|
| 人間承認（案 A / OQ-M01〜M03） | ✅ **2026-08-14** |
| ブランチ `feature/loop-14-mobile-ui` | ✅ |
| ハンバーガーナビ + カレンダー縮小 | ✅ |
| テスト（Testcontainers） | ⏭ Docker Desktop 未起動のためスキップ |
| Consistency Review（`roles.md` §12） | ✅ **2026-08-14** |
| UI ローカル確認 | ✅ **2026-08-14**（人間確認） |
| Loop 14 完了（PR #9 / merge） | ✅ **2026-08-14** |

## Loop 11 Progress

| 作業 | 状態 |
|---|---|
| ブランチ作成 `feature/loop-11-schedule` | ✅ |
| FR-S01 / OQ-S01 設計草案 | ✅ |
| 人間承認（推奨案） | ✅ **2026-08-13** |
| Flyway V7 + スケジュール UI | ✅ |
| WEEKLY 複数曜日（V8 / DD-18） | ✅ |
| イベント生成 | ✅ |
| テスト | ✅（Testcontainers は Docker 依存でスキップ可） |
| Consistency Review（`roles.md` §12） | ✅ **2026-08-13** |
| UI ローカル確認 | ✅ **2026-08-13** |
| Loop 11 完了（PR #6 / merge） | ✅ **2026-08-14** |

## Loop 10 Progress

| 作業 | 状態 |
|---|---|
| ブランチ作成 `feature/loop-10-notification` | ✅ |
| FR-N01〜N03 / OQ-08 設計草案 | ✅ |
| 人間承認（推奨案） | ✅ |
| Flyway V6 + お知らせ UI | ✅ |
| 既読・未読表示 | ✅ |
| ローカル確認 | ✅ **2026-08-13** |
| Loop 10 完了（PR #4 / merge） | ✅ **2026-08-13** |

## Loop 09 Progress

| 作業 | 状態 |
|---|---|
| ブランチ作成 `feature/loop-09-instructor-assignment` | ✅ |
| OQ-03 / FR-I05 / FR-I06 設計草案 | ✅ |
| 人間承認（推奨案） | ✅ |
| Flyway V5 + Event 担当講師 UI | ✅ |
| 講師稼働表示 | ✅ |
| ローカル確認（既存 PG / profile=`local`） | ✅ |
| Testcontainers | ⏭ Docker Desktop パイプ不整合のためスキップ |
| Loop 09 完了（PR #2 / merge） | ✅ **2026-08-13** |

## Loop 08 Progress

| 作業 | 状態 |
|---|---|
| 要件・設計の整理（FR-I01 等） | ✅ |
| Open Questions（OQ-I01〜I04）提示 | ✅ |
| 人間承認（設計確定） | ✅ **2026-08-12**（推奨案＋DD-08） |
| DB / Entity 実装（Flyway V3） | ✅ |
| Controller / Service / 画面 | ✅ |
| InstructorServiceTest / SecurityTest | ✅ |
| テスト・ローカル確認 | ✅ **2026-08-12** |
| Loop 08 完了 | ✅ **2026-08-12** |

## Loop 07 Progress

| 作業 | 状態 |
|---|---|
| Event / EventAttendance Entity + Repository | ✅ |
| EventService（作成・カレンダー・参加・定員） | ✅ |
| EventController / CalendarController | ✅ |
| Thymeleaf 画面（S03・S10・S11） | ✅ |
| EventServiceTest / EventControllerSecurityTest / CalendarRenderTest | ✅ |
| ローカル動作確認（ADMIN / PARENT） | ✅ **2026-08-11** |
| Loop 07 完了 | ✅ **2026-08-11** |

## Loop 06 Progress

| 作業 | 状態 |
|---|---|
| Parent / Child Entity + Repository | ✅ |
| FamilyService（household_id 認可 + CRUD） | ✅ |
| FamilyController（一覧・詳細・ADMIN 削除） | ✅ |
| MyFamilyController（家庭・保護者・子ども CRUD） | ✅ |
| Thymeleaf 画面（S04〜S09） | ✅ |
| FamilyServiceTest / FamilyControllerSecurityTest | ✅ |
| ローカル動作確認（PARENT / ADMIN） | ✅ **2026-08-11** |
| Loop 06 完了 | ✅ **2026-08-11** |

## Loop 05 Progress

| 作業 | 状態 |
|---|---|
| User / Household Entity + Repository | ✅ |
| MinsukeUserDetails + UserDetailsService | ✅ |
| SecurityConfig（フォームログイン・ロール認可・CSRF） | ✅ |
| AuthService / AuthController（PARENT 登録） | ✅ |
| ログイン・登録・カレンダー画面 | ✅ |
| ナビゲーション共通ヘッダー | ✅ |
| プレースホルダ（families / my-family / events/new） | ✅ |
| AuthServiceTest / AuthControllerSecurityTest | ✅ |
| SD-01 BCrypt 承認 | ✅ **2026-08-11** |
| Loop 05 完了 | ✅ **2026-08-11** |

## Loop 04 Progress

| 作業 | 状態 |
|---|---|
| Spring Boot プロジェクト（pom.xml, mvnw） | ✅ |
| パッケージ構成 `com.minsuke` | ✅ |
| SecurityConfig（permitAll） | ✅ |
| application.yml / local プロファイル | ✅ |
| Flyway V1 + dev seed V2 | ✅ |
| docker-compose.yml | ✅ |
| 最小 UI（index）+ `/health` | ✅ |
| Maven ビルド（`mvnw package` / test） | ✅ JDK 21 で確認 |
| Testcontainers テスト | ✅ `mvnw test` 成功 |
| Docker Compose 起動確認 | ✅ **2026-08-11 完了** |
| Flyway マイグレーション適用 | ✅ V1 + V2（local） |
| アプリ起動（`/health`） | ✅ UP |
| JDK 21 ビルド | ✅ Temurin 21.0.12 |
| Loop 04 完了 | ✅ **2026-08-11** |

## Loop 03 Progress（完了）

| 作業 | 状態 |
|---|---|
| ER 図確定（6 テーブル） | ✅ |
| 全テーブルカラム定義 | ✅ `database.md` v1.1 |
| FK / ON DELETE 方針 | ✅ |
| 部分 UNIQUE（参加重複防止） | ✅ **Approved**（DD-05） |
| 削除ポリシー（DQ-02 / DD-01） | ✅ **Approved** |
| Flyway SQL 設計 | ✅ `docs/database/` |
| 人間承認（DD-01〜05） | ✅ **2026-08-11** |
| Loop 03 完了 | ✅ **2026-08-11** |

## Loop 02 Progress（完了）

| 作業 | 状態 |
|---|---|
| Java / Spring Boot バージョン | ✅ **Approved**（AD-05） |
| Maven ビルド | ✅ **Approved**（AD-08） |
| Flyway 採用 | ✅ **Approved**（AD-06） |
| Docker Compose | ✅ **Approved**（AD-07） |
| Spring Security タイミング（OQ-09） | ✅ 確定 |
| パッケージ構成 | ✅ **Approved** |
| カレンダー → event 統合（AD-04） | ✅ **Approved** |
| 初回 ADMIN 方針（OQ-12） | ✅ **Approved** |
| 人間承認（AD-04〜08） | ✅ **2026-08-11** |
| Loop 02 完了 | ✅ **2026-08-11** |

## Loop 01 Progress（完了）

| 作業 | 状態 |
|---|---|
| 要件整理（PM / BA） | ✅ 更新済（v0.2） |
| アーキテクチャ検討 | ✅ **技術選定承認済** |
| DB 候補整理 | ✅ 個別参加モデル反映 |
| セキュリティ方針 | ✅ ADMIN・個別参加反映 |
| UI 方針 | ✅ 個別参加 UI 反映 |
| テスト方針 | ✅ `development-roadmap.md` §6 で整理済 |
| 個人情報ポリシー | ✅ **選択肢 B 承認済** |
| Reviewer 確認 | ✅ **2026-08-11 実施**（§17 参照） |
| Loop 01 完了 | ✅ **2026-08-11 完了** |

---

# 2. Loop 01 Goal

> **MinSukeの目的・ユーザー・業務フロー・必要機能・MVP・基本アーキテクチャ・DB・セキュリティ・テスト方針を整理し、開発ロードマップを決定する。**

---

# 3. Confirmed Decisions（2026-08-10）

| ID | 決定内容 |
|---|---|
| **CTX-01** | 主たる利用シーン: **汎用** |
| **MVP-01** | MVP に **ADMIN ロールを含む** |
| **MVP-02** | **イベント作成は ADMIN のみ** |
| **MVP-03** | 参加単位: **保護者・子ども個別**（定員 = 参加者人数） |
| **AD-01** | モノリシック Web アプリ — **Approved** |
| **AD-02** | Thymeleaf SSR — **Approved** |
| **AD-03** | PostgreSQL + JPA — **Approved** |
| **PD-01** | 個人情報保持・削除: **選択肢 B（標準保持）** |

### Loop 02 Confirmed（2026-08-11）

| ID | 決定内容 |
|---|---|
| **AD-04** | カレンダーは `event` モジュールに統合 — **Approved** |
| **AD-05** | Java **21** + Spring Boot **3.5.x** — **Approved** |
| **AD-06** | **Flyway** によるスキーマ管理 — **Approved** |
| **AD-07** | **Docker Compose** を標準ローカル DB 環境 — **Approved** |
| **AD-08** | **Maven** + Maven Wrapper — **Approved** |

### Loop 03 Confirmed（2026-08-11）

| ID | 決定内容 |
|---|---|
| **DD-01** | `deleted_at` なし。プロフィール物理削除、参加は `status` 管理 — **Approved** |
| **DD-02** | 監査ログテーブルは MVP 外 — **Approved** |
| **DD-03** | DB カラム暗号化は MVP 外 — **Approved** |
| **DD-04** | users の role / household_id CHECK 制約 — **Approved** |
| **DD-05** | 参加重複防止は部分 UNIQUE（REGISTERED のみ） — **Approved** |

---

# 4. Project Vision

**Confirmed 方向:**

汎用的な家庭・イベント管理 Web アプリ。  
MVP は **認証 + ADMIN によるイベント管理 + 家庭管理 + 保護者・子ども個別参加**。

---

# 5. Target Users

| ユーザー | MVP | 備考 |
|---|---|---|
| 保護者（PARENT） | Yes | 自家庭管理、個別参加登録 |
| 管理者（ADMIN） | Yes | イベント作成 |
| 講師 | No | Post-MVP |

---

# 6. MVP Scope

## 含む

- ユーザー登録・ログイン
- **ADMIN ロール** — イベント作成専用
- 家庭・保護者・子ども管理
- カレンダー表示
- **保護者・子ども個別**の参加登録・定員管理（人数単位）

## 含まない

- 講師管理、スケジュール、通知、一括登録

---

# 7. Requirements Status

| Category | Status |
|---|---|
| Project Goal | ✅ Confirmed 方向 |
| Target Users | ✅ Confirmed |
| Business Flow | ✅ Updated |
| Functional Requirements | ✅ Updated |
| MVP | ✅ Confirmed |
| Technology Stack | ✅ **Approved** |
| Database | ✅ Updated（個別参加） |
| Personal Data Policy | ✅ **選択肢 B** |
| Deployment | Open |

---

# 8. Architecture Status

**Loop 01 確定:**
- Java + Spring Boot + Thymeleaf + PostgreSQL + JPA
- モノリシック構成

**Loop 02 確定（2026-08-11）:**
- Java 21 / Spring Boot 3.5.x / Maven（AD-05, AD-08）
- Flyway + Docker Compose / PostgreSQL 16（AD-06, AD-07）
- パッケージ `com.minsuke`（auth / family / event / common）
- カレンダーは `event` モジュール内（AD-04）
- Spring Security: Loop 04 依存追加 → Loop 05 本格化

---

# 9. Database Status

**Loop 03 確定（2026-08-11）:**

- **6 テーブル:** households, users, parents, children, events, event_attendances
- `users`: ADMIN は `household_id NULL`、PARENT は NOT NULL（DD-04 CHECK）
- `event_attendances`: 部分 UNIQUE（DD-05）、status でキャンセル管理（DD-01）
- Flyway 設計 SQL: `docs/database/V1__create_schema.sql`, `V2__seed_dev.sql`
- 監査ログ・DB 暗号化: MVP 外（DD-02, DD-03）

---

# 10. Security Status

- ADMIN のみイベント作成
- PARENT は自家庭の保護者・子どものみ参加登録可能
- **Spring Security** 採用（Interceptor 不採用）— Loop 04/05 で実装

---

# 11. Open Questions

| # | 質問 | 状態 |
|---|---|---|
| OQ-01 | 利用シーン | ✅ 汎用 |
| OQ-02 | ADMIN in MVP | ✅ 含む |
| OQ-05 | 個人情報保持・削除 | ✅ **選択肢 B（標準保持）** |
| OQ-03 | スケジュールとイベントの関係 | Closed — イベント中心（案 A） |
| OQ-04 | 子どもの直接ログイン | Open |
| OQ-06 | 同時利用規模 | Open |
| OQ-07 | 本番デプロイ | Open |
| OQ-09 | Spring Security タイミング | ✅ Loop 04 依存 / Loop 05 本格化 |
| OQ-10 | 参加単位 | ✅ 個別 |
| OQ-11 | 技術選定 | ✅ 承認済 |
| OQ-12 | 初回 ADMIN の作成方法 | ✅ seed / 登録は PARENT のみ |
| OQ-P01〜P05 | 個人情報・決済・お知らせメールの外部化 | Open（後続 Loop。`minutes.md` §33） |
| OQ-R01〜R06 | ADMIN 参加状況・家庭参加率 | Open（Loop 17 設計。`minutes.md` §34） |

---

# 12. Decisions Log

## AD-01〜03 — 技術選定

- **Date:** 2026-08-10
- **Approved:** **Yes**
- **Stack:** Spring Boot + Thymeleaf + PostgreSQL

## CTX-01 — 利用シーン

- **Date:** 2026-08-10
- **Decision:** 汎用
- **Approved:** Yes

## MVP-02 — イベント作成権限

- **Date:** 2026-08-10
- **Decision:** ADMIN のみ
- **Approved:** Yes

## MVP-03 — 参加単位

- **Date:** 2026-08-10
- **Decision:** 保護者・子ども個別。定員は人数カウント
- **Impact:** `event_attendances` スキーマ変更、UI に参加者チェックリスト
- **Approved:** Yes

## PD-01 — 個人情報保持・削除ポリシー

- **Date:** 2026-08-11
- **Decision:** 選択肢 B（標準保持）— 退会後30日、参加記録3年、ログ1年、バックアップ90日
- **Reason:** 汎用利用シーンに適合。参加履歴の一定期間保持と個人情報リスクのバランス
- **Approved:** Yes

## SD-02 — Spring Security 導入タイミング

- **Date:** 2026-08-11
- **Decision:** Loop 04 で依存追加（permitAll）、Loop 05 で本格実装
- **Reason:** セキュリティ by Design。旧 Interceptor 方式は不採用
- **Approved:** Yes

## AD-04〜08 — Loop 02 技術選定詳細

- **Date:** 2026-08-11
- **Approved:** **Yes**（ユーザー承認）
- **内容:** カレンダー→event統合、Java 21 + Boot 3.5.x、Flyway、Docker Compose、Maven

## SD-03 — 初回 ADMIN 作成

- **Date:** 2026-08-11
- **Decision:** 公開登録は PARENT のみ。ADMIN は Flyway seed（dev/local）
- **Approved:** Yes

## DD-01〜05 — Loop 03 データベース設計

- **Date:** 2026-08-11
- **Approved:** **Yes**（ユーザー承認）
- **内容:** 削除ポリシー、監査ログ MVP 外、暗号化 MVP 外、CHECK 制約、部分 UNIQUE

---

# 13. Risks

| ID | リスク | 更新 |
|---|---|---|
| R04 | 権限複雑化 | ADMIN 確定により一部緩和。個別参加で UI 複雑化に注意 |
| R05 | 定員計算 | 家庭単位→人数単位に変更。Service 層で COUNT 必須 |

---

# 14. Loop 01 Deliverables

| ドキュメント | 状態 |
|---|---|
| requirements.md | ✅ v0.3 |
| architecture.md | ✅ v0.4 |
| database.md | ✅ v1.1 |
| security.md | ✅ Updated |
| ui.md | ✅ Updated |
| development-roadmap.md | ✅ |
| minutes.md | ✅ 本更新 |

---

# 15. Loop 01 Completion Criteria

| 項目 | 状態 |
|---|---|
| MVP 定義 | ✅ |
| 技術選定 | ✅ |
| 個人情報ポリシー | ✅ 選択肢 B |
| Reviewer | ✅ 2026-08-11 |
| **Loop 01 完了** | ✅ **2026-08-11** |

---

# 17. Reviewer Report（2026-08-11）

## 総合判定

**承認** — OQ-05 確定により Loop 01 完了条件を満たす。

## 確認結果

| 観点 | 結果 | コメント |
|---|---|---|
| 要件漏れ | ⚠️ 軽微 | ADMIN 初回作成方法が未整理（→ OQ-12 追加） |
| 要件の矛盾 | ✅ なし | MVP・個別参加・ADMIN 権限は全ドキュメントで整合 |
| 過剰設計 | ✅ 問題なし | モノリシック + Thymeleaf は MVP に適切 |
| 技術選定 | ✅ 妥当 | AD-01〜03 承認済、旧 MinSuke 知見と整合 |
| セキュリティ | ⚠️ 条件付き | OQ-05 確定後に `security.md` §8 を更新要 |
| 拡張性 | ✅ 妥当 | Post-MVP エンティティは `database.md` §5 で分離 |
| テスト可能性 | ✅ 妥当 | Service 中心のレイヤー設計、Loop 別テスト計画あり |
| 実現可能性 | ✅ 妥当 | Loop 04〜07 で MVP 到達可能な範囲 |

## 指摘事項（ブロッカー）

なし — OQ-05 は 2026-08-11 に選択肢 B で確定済み。

## 指摘事項（非ブロッカー — Loop 02 以降で整理可）

| ID | 内容 | 推奨タイミング |
|---|---|---|
| **OQ-12** | 初回 ADMIN の作成方法（登録時ロール付与？ seed のみ？） | Loop 05 前 |
| **OQ-03** | スケジュールとイベントの関係 | Loop 09 前 |
| **OQ-04** | 子どもの直接ログイン | Loop 05 前 |
| **OQ-09** | Spring Security 導入タイミング | Loop 02 |
| **DQ-02** | 論理削除 vs 物理削除 | Loop 03 |
| **UI-OQ** | 家族一覧（S04）を全認証ユーザーが閲覧 — 意図確認 | Loop 06 前 |

## Reviewer 所見

設計書間の整合性は良好。MVP 境界が明確で、個別参加モデルが DB・セキュリティ・UI に一貫して反映されている。**Loop 01 は完了。**

---

# 15.1 Consistency Report — Loop 06 完了時（2026-08-11）

| 区分 | 初回 | 是正後 |
|---|---|---|
| Blocker | 0 | 0 |
| Warning | 5 | 0 |

## Warnings（是正状況）

| ID | 内容 | 状態 |
|---|---|---|
| CON-01 | 起動 URL / ポート表記（8080 vs 8081） | ✅ `minutes.md` / `architecture.md` 更新 |
| CON-02 | `application-local.yml` 手動作成 | ✅ `application-local.yml.example` 追加 |
| CON-03 | `Composer.md` §17 Loop 順序 | ✅ `development-roadmap.md` と同期 |
| CON-04 | `events` Entity 未実装 | ✅ Loop 07 で `Event` / `EventAttendance` 実装 |
| CON-05 | Testcontainers / CI | ✅ `architecture.md` / `development-roadmap.md` に方針追記 |

## Verified ✅

| 観点 | 結果 |
|---|---|
| A. Loop 状態 | `minutes.md` / `Composer.md` / `roadmap` / `roles.md` — Loop 07 完了、MVP 完了 |
| B. DB ↔ Entity | `events`, `event_attendances` — Flyway V1 と JPA 一致 |
| C. Security ↔ URL | `POST /events`（ADMIN）、`POST /events/*/attend`（PARENT）— 設定と Controller 一致 |
| E. テンプレート | Loop 07 全画面（`calendar`, `event/*`）が Controller return と対応 |
| B. dev seed | BCrypt ハッシュ = `password`（Spring 標準ハッシュ） |
| C. Security ↔ URL | `/login`, `/register`, `/my-family/**`, `/events/new`, `POST /families/*/delete` — 設定と Controller 一致 |
| C. Service 認可 | `FamilyService` — PARENT は `household_id` で自家庭のみ変更 |
| D. Docker ↔ DB | `5433:5432` ↔ `jdbc:postgresql://localhost:5433/minsuke` |
| D. Flyway local | `LocalFlywayConfig` — dev seed 変更時に repair |
| E. テンプレート | Loop 06 全画面（`family/*`）が Controller return と対応 |
| E. ナビ | `fragments/header.html` — 実装済み URL のみリンク |
| F. ビルド | `mvnw test` 成功（Testcontainers は Docker 依存でスキップ可） |

---

# 16. Next Loop

**Loop 08 / 09 / 10 / 11 / 12 / 13 / 14 / 15 / 16 / 17** — Loop 17 完了（人間 UI 確認済）。  
**次 Loop:** 人間承認待ち。候補: Testing/CI、個人情報・決済・メールの外部化（`minutes.md` §33）。

---

# 25. Loop 09 — Instructor Assignment & Availability（2026-08-12）

## 目的

イベントに担当講師を設定し（FR-I05）、講師の稼働状況を可視化する（FR-I06）。

## スコープ（Approved）

| 含む | 含まない |
|---|---|
| OQ-03 = **イベント中心（案 A）** | 独立 `schedules`（FR-S01 本格化） |
| `events.instructor_id`（任意・単一） | 複数講師同時担当 |
| イベント作成・編集・詳細での担当講師 | INSTRUCTOR ログイン |
| 講師詳細の稼働（一覧・月次件数） | FR-S03/S04 |

## 承認事項（2026-08-12 — 推奨案で承認）

| ID | 質問 | 決定 |
|---|---|---|
| **OQ-03** | スケジュールとイベントの関係 | **案 A: イベント中心**（独立 schedules は後続） |
| **DD-09** | `events.instructor_id` NULL FK、ON DELETE SET NULL | **Approved** |
| **DD-10** | 稼働は events 集計のみ（専用テーブルなし） | **Approved** |
| **単一講師** | 1 イベント = 最大 1 講師 | **Approved** |
| **S17** | イベント編集画面を追加して担当変更 | **Approved** |

## 設計サマリー

| 領域 | 内容 |
|---|---|
| DB | `V5__events_instructor.sql` |
| UI | S10/S11 拡張 + S17 編集 + S14 稼働セクション |
| 稼働 | 今後の担当イベント一覧、月次件数 |

## 次アクション

完了（PR #2 merge 済）。  
**Loop 10 設計中。** 次アクション: OQ-08 / DD-11 / DD-12 の人間承認 → 実装。

---

# 26. Loop 10 — Notification（設計草案 2026-08-13）

## 目的

アプリ内お知らせを作成・配信し、既読を管理する（FR-N01〜N03）。

## スコープ（Approved）

| 含む | 含まない |
|---|---|
| アプリ内お知らせ（作成・一覧・詳細） | メール／プッシュ（将来 Loop） |
| 配信対象 = 認証済み全員 | 家庭単位・ロール別の細かい配信 |
| ユーザー単位の既読 | 下書きフロー |
| ヘッダー未読件数 | FR-S01 本格スケジュール |

## 承認事項（2026-08-13 — 推奨案で承認）

| ID | 質問 | 決定 |
|---|---|---|
| **OQ-08** | お知らせの配信チャネル | **アプリ内のみ**（メールは将来 Loop） |
| **DD-11** | `announcements` + `announcement_reads` | **Approved** |
| **DD-12** | 配信対象は認証済み全員（絞り込みなし） | **Approved** |
| **公開モデル** | 作成即公開・下書きなし | **Approved** |
| **編集・削除** | ADMIN のみ編集・削除可 | **Approved** |

## 設計サマリー

| 領域 | 内容 |
|---|---|
| DB | `V6__create_announcements.sql` |
| UI | S18 一覧 / S19 詳細（閲覧で既読）/ S20 作成・編集 |
| 認可 | 作成・編集・削除 = ADMIN、閲覧 = 認証済み |

## 次アクション

テスト・ローカル確認済。PR #4 merge 済。  
**Loop 11 実装中**（承認済）。次: ローカル確認 → PR。

---

# 27. Loop 11 — Schedule Management（設計草案 2026-08-13）

## 目的

定期・単発スケジュールを本格管理し（FR-S01）、必要に応じてイベント（参加単位）を生成する。

## 背景

- Loop 09（OQ-03）で **イベント中心** を採用。担当講師は `events.instructor_id`。
- FR-S01 本格化のタイミングで **独立 `schedules` テーブル** を導入し、テンプレートとインスタンスを分離する。

## スコープ案（Proposed）

| 含む | 含まない |
|---|---|
| `schedules` マスタ CRUD（ADMIN） | **FR-S03 参加登録単位**（家庭/保護者/子ども — 後続 Loop） |
| 種別: **ONE_OFF / WEEKLY** | FR-S04 一括登録 |
| `events.schedule_id`（任意） | 複雑な RRULE（毎月第2火曜等） |
| スケジュールからイベント生成（N 週） | INSTRUCTOR ログイン |
| 生成時に講師・定員等をコピー | 自動バッチ（cron） |

## 承認が必要な事項

| ID | 質問 | 推奨案 |
|---|---|---|
| **OQ-S01** | スケジュールとイベント（本格化） | **テンプレート + インスタンス**（`schedules` + `events.schedule_id`） |
| **DD-14** | `schedules` テーブル追加 | **Approve** |
| **DD-15** | 繰り返し MVP = ONE_OFF + WEEKLY | **Approve** |
| **DD-18** | WEEKLY 複数曜日 | **Approve** ✅ **2026-08-13**（人間要望） |
| **DD-16** | `events.schedule_id` NULL FK、ON DELETE SET NULL | **Approve** |
| **DD-17** | 生成イベントへ schedule の講師・定員等をコピー | **Approve** |
| **生成単位** | デフォルト 4 週、ADMIN が実行 | **Approve** ✅ **2026-08-13** |

## 承認記録

| 日付 | 内容 |
|---|---|
| 2026-08-13 | OQ-S01 / DD-14〜17 を推奨案のまま承認。FR-S03 参加登録単位は後続 Loop へ延期。 |
| 2026-08-13 | **DD-18** WEEKLY 複数曜日指定を承認（実装依頼）。 |

## 設計サマリー

| 領域 | 案 |
|---|---|
| DB | `docs/database/V7__create_schedules.sql` + `V8__schedule_weekdays.sql` |
| UI | S21 一覧 / S22 詳細 / S23 作成・編集 / 詳細から「イベント生成」 |
| カレンダー | 既存 `events` 表示のまま（生成されたイベントが出る） |

## Future（Loop 11 以降 — 2026-08-13 人間要望）

**FR-S03 — 参加登録単位**

スケジュール作成時に、参加を受け付ける単位を **家庭 / 保護者 / 子ども** のいずれかで設定し、参加登録 UI ではその単位のみ選択可能にする。

| 単位 | 参加登録 |
|---|---|
| 家庭 | 家庭単位のみ（保護者・子ども個別は不可） |
| 保護者 | 保護者のみ |
| 子ども | 子どものみ |

- 現状: 全イベントで保護者・子ども個別の両方が選択可能（OQ-10 / MVP）。
- **Loop 11 では実装しない。** 案: `schedules.participation_unit` → 生成 `events` へコピー（OQ-S02）。

## 次アクション

**完了**（PR #6 merge 2026-08-14）。続きは Loop 12（FR-S03）。

## 参照

- `Composer.md` §4.9
- `requirements.md` §6.3 Loop 11
- `database.md` §17
- `roles.md` §12 Consistency Engineer

---

# 27.1 Consistency Report — Loop 11（2026-08-13）

Consistency Engineer（`roles.md` §12）による横断確認。

| 区分 | 件数 |
|---|---|
| Blocker | 0（是正済 1 件含む） |
| Warning | 0（是正済 7 件） |

### Blockers（是正済）

| ID | 内容 | 状態 |
|---|---|---|
| CON-L11-01 | Hibernate validate: `schedules.day_of_week` DB=`SMALLINT` / Entity=`Integer`→`INTEGER` 期待で起動失敗 | ✅ `@JdbcTypeCode(SqlTypes.SMALLINT)` で是正。起動確認済（`Started MinsukeApplication`） |

### Warnings（是正済）

| ID | 内容 | 状態 |
|---|---|---|
| CON-L11-02 | `Composer.md` がフェーズ A / Proposed のまま | ✅ フェーズ B / Approved に更新 |
| CON-L11-03 | `database.md` DD-14〜17 が Proposed、V7 未配置表記 | ✅ Approved、migration 適用済と明記 |
| CON-L11-04 | `requirements.md` FR-S01/S02・OQ-S01 が Proposed/Open | ✅ Approved |
| CON-L11-05 | `security.md` Loop 11 認可が Proposed | ✅ Approved |
| CON-L11-06 | `development-roadmap.md` / `minutes.md` §16・§18 が「設計中」 | ✅ 実装中に同期 |
| CON-L11-07 | `Composer.md` §17 例示で Loop 11=Mobile UI（初期案名） | ✅ 現行 Loop 11=Schedule と注記 |

### Verified ✅

| 観点 | 結果 |
|---|---|
| A. 設計書 ↔ 設計書 | OQ-S01 / DD-14〜17 / FR-S03 後続 — `Composer` / `minutes` / `requirements` / `database` / `security` / `ui` 一致 |
| B. DB ↔ Entity | `schedules` 全列 + `events.schedule_id` ↔ `Schedule` / `Event`。Flyway V7 success。docs↔src V7 バイナリ一致 |
| C. Security ↔ URL | `/schedules/**` ADMIN。Service `requireAdmin`。PARENT 403 テストあり |
| D. 環境 | PG `5433` healthy、profile=`local`、port `8081` |
| E. 画面 ↔ Controller | `schedule/list|detail|form` 存在。header「スケジュール」ADMIN。イベント詳細に元スケジュールリンク |
| F. テスト | `ScheduleServiceTest` / `ScheduleControllerSecurityTest`。compile OK。Testcontainers は Docker パイプ不整合時スキップ可 |
| スコープ外 | FR-S03 参加登録単位 — 未実装（意図どおり） |

### エージェント運用ルール（人間指示 2026-08-13）

以降の開発では **`roles.md` / `minutes.md` / `Composer.md` を絶対参照**する。実装 Loop 完了前に Consistency Engineer（§12）を必ず通す。

---

# 27.2 Consistency Report — Loop 11 DD-18 複数曜日（2026-08-13）

人間要望により WEEKLY の曜日を複数指定可能にした。V7 は immutable のため **V8** で移行。

| 区分 | 件数 |
|---|---|
| Blocker | 0 |
| Warning | 0 |

### Verified ✅

| 観点 | 結果 |
|---|---|
| A. 設計書 | DD-18 を `Composer.md` / `minutes.md` / `database.md` / `requirements.md` に記録 |
| B. DB ↔ Entity | Flyway V8 適用成功。`schedule_weekdays` ↔ `@ElementCollection`。Hibernate validate 通過 |
| C. 生成 | 選択した全曜日の日付でイベント生成。重複日付は従来どおりスキップ |
| E. 画面 | フォームはチェックボックス。詳細は「月曜日・水曜日」形式 |
| F. テスト | `weeklyScheduleGeneratesEventsForAllSelectedDays` を追加 |

---

# 28. Loop 12 — Participation Unit（承認済 2026-08-14）

## 目的

スケジュール／イベントごとに参加登録単位を **家庭 / 保護者 / 子ども** のいずれかへ制限する（FR-S03 / OQ-S02）。

## 背景

- Loop 07〜11: 全イベントで保護者・子ども個別の両方が選択可能（OQ-10）。
- Loop 11: スケジュール本格化。FR-S03 は延期。
- 2026-08-13 人間要望: 単位を設定し、UI ではその単位のみ選択可能にする。

## スコープ（Approved）

| 含む | 含まない |
|---|---|
| `schedules.participation_unit` / `events.participation_unit` | FR-S04 一括登録 |
| 生成時に単位をコピー | 複雑 RRULE |
| 参加登録 UI の制限（表示 + Service 拒否） | Mobile UI |
| HOUSEHOLD 参加（1 家庭 = 定員 1） | INSTRUCTOR ログイン |

## 承認が必要な事項

| ID | 質問 | 推奨案 |
|---|---|---|
| **OQ-S02** | 単位をどこに持つか | **両方** — schedule に持ち、生成イベントへコピー。手作りイベントは event に直接 |
| **DD-19** | 列 `participation_unit`（HOUSEHOLD / PARENT / CHILD、NULL 可） | **Approve** |
| **DD-20** | 既存・NULL は現行どおり PARENT+CHILD | **Approve**（後方互換） |
| **DD-21** | HOUSEHOLD = `event_attendances.participant_type=HOUSEHOLD`、定員 1 家庭 = 1 | **Approve** |
| **新規必須** | 新規スケジュール／イベントは単位を必須選択 | **Approve** ✅ **2026-08-14** |

## 承認記録

| 日付 | 内容 |
|---|---|
| 2026-08-14 | OQ-S02 / DD-19〜21 を推奨案のまま承認。 |

## 設計サマリー

| 領域 | 案 |
|---|---|
| DB | `docs/database/V9__participation_unit.sql` → `src/main/resources/db/migration/V9__participation_unit.sql` |
| UI | S23 / S10 / S17 に単位選択。S11 は単位に応じて選択肢を絞る |
| 認可 | 単位外の POST `/events/{id}/attend` は Service で拒否 |

## 次アクション

実装完了。ローカル画面確認後に Loop 12 完了とする。

## 参照

- `Composer.md` §4.10
- `requirements.md` §6.3 FR-S03
- `database.md` §18

---

# 28.1 Consistency Report — Loop 12（2026-08-14）

Consistency Engineer（`roles.md` §12）による横断確認。

| 区分 | 件数 |
|---|---|
| Blocker | 0 |
| Warning | 1（残） / 是正済 6 |

### Blockers

（なし）

### Warnings（是正済）

| ID | 内容 | 状態 |
|---|---|---|
| CON-L12-01 | `requirements.md` FR-S03 詳細 / OQ-S02 が Proposed | ✅ Approved に更新 |
| CON-L12-02 | `database.md` OQ-S02 が Proposed、V9 が草案表記 | ✅ Approved、migration 適用対象と明記 |
| CON-L12-03 | `minutes.md` §16・§18・§28 が「設計中」 | ✅ 実装完了・ローカル確認待ちに同期 |
| CON-L12-04 | `development-roadmap.md` / `Composer.md` がフェーズ A 表現 | ✅ フェーズ B 完了・確認待ちに更新 |
| CON-L12-05 | `ui.md` S10/S11/S17/S23 に単位未記載 | ✅ Loop 12 注記を追加 |
| CON-L12-07 | UI ローカル確認 | ✅ **2026-08-14** 人間確認で解消 |

### Warnings（残）

| ID | 内容 | 扱い |
|---|---|---|
| CON-L12-06 | Testcontainers: Docker Desktop 未起動のため `EventServiceTest` / `ScheduleServiceTest` 等スキップ | 従来どおり。ローカル確認で代替 |

### Verified ✅

| 観点 | 結果 |
|---|---|
| A. 設計書 ↔ 設計書 | OQ-S02 / DD-19〜21 / FR-S03 — `Composer` / `minutes` / `requirements` / `database` / `security` / `ui` 一致 |
| B. DB ↔ Entity | `schedules.participation_unit` / `events.participation_unit` VARCHAR(20) ↔ `@Enumerated(STRING)`。`participant_type` length 20 + HOUSEHOLD CHECK。docs↔src V9 一致 |
| C. Security ↔ URL | 単位設定は既存 ADMIN URL（`/events/**` create/edit、`/schedules/**`）。単位外参加は `EventService.requireUnitAllows`。CSRF は attend フォームに `_csrf` |
| D. 環境 | ポート方針は従来どおり（PG `5433`、app `8081`、profile=`local`）。本確認時 Docker 未起動 |
| E. 画面 ↔ Controller | `event/create|edit|detail`、`schedule/form|detail` に単位。HOUSEHOLD は `participantType=HOUSEHOLD` で POST |
| F. テスト | `EventServiceTest`（CHILD 拒否・HOUSEHOLD 1 slot）/ `ScheduleServiceTest`（generate copy）。`mvnw compile test-compile` 成功。実行は Docker 依存でスキップ |
| スコープ外 | FR-S04・RRULE・Mobile UI・INSTRUCTOR ログイン — 未実装（意図どおり） |

---

# 29. Loop 13 — 自家庭の参加可視化（承認済 2026-08-14）

## 目的

保護者が、自分の家族が参加しているイベントをカレンダー上で判別できるようにする（FR-E06 / OQ-E01）。

## 背景

- 現行カレンダー（S03）は全イベント同一色。満員のみ赤。
- `buildCalendarView` はユーザー／家庭を見ない。参加情報はイベント詳細（S11）まで分からない。
- 2026-08-14 人間要望: カレンダーの色を変える、本日の参加イベントを表示する、など。後続 Loop でよい。

## 現状

- イベントチップ: 青（通常）/ 赤（満員）
- データ: `event_attendances`（household_id + REGISTERED）で自家庭の参加は既に取れる
- **新テーブル不要**

## スコープ（Approved）

| 含む | 含まない |
|---|---|
| カレンダーで「自家庭が参加中」の色分け | 他家庭の参加の可視化 |
| カレンダー上部に「本日の参加」一覧 | FR-E05 履歴ページ |
| PARENT（household あり）向け | Mobile UI 専用画面 |
| 満員表示の維持（参加中+満員は参加色+「満」） | FR-S04 一括登録 |

## 承認が必要な事項

| ID | 質問 | 推奨案 |
|---|---|---|
| **OQ-E01** | どう見せるか | **両方** — カレンダー色 + 本日の参加一覧 |
| **DD-22** | 参加の定義 | 自家庭の REGISTERED が 1 件以上（単位は問わない） |
| **対象ロール** | ADMIN も色分けするか | **しない**（ADMIN は household なし。現状どおり全イベント表示） |

## 承認記録

| 日付 | 内容 |
|---|---|
| 2026-08-14 | OQ-E01 / DD-22 を推奨案のまま承認。Loop 12 の PR とは分離し、merge 後に実装。 |

## 設計サマリー（Approved）

| 領域 | 案 |
|---|---|
| DB | 変更なし。月内 event_id × household の REGISTERED を 1 クエリ |
| DTO | `CalendarEventDTO.participating`、任意で `todayParticipations` |
| UI | S03。凡例（通常 / 参加中 / 満員）。本日一覧は当日かつ participating |
| 認可 | 自 household のみ。他家庭の出席は読まない |

## 次アクション

実装完了。ローカル確認済。次アクション: commit / PR。

## 参照

- `requirements.md` FR-E06 / OQ-E01
- `ui.md` S03

---

# 29.1 Consistency Report — Loop 13（2026-08-14）

Consistency Engineer（`roles.md` §12）による横断確認。

| 区分 | 件数 |
|---|---|
| Blocker | 0 |
| Warning | 1（残） / 是正済 1 |

### Blockers

（なし）

### Warnings（是正済）

| ID | 内容 | 状態 |
|---|---|---|
| CON-L13-02 | UI ローカル確認 | ✅ **2026-08-14** 人間確認で解消 |

### Warnings（残）

| ID | 内容 | 扱い |
|---|---|---|
| CON-L13-01 | Testcontainers 未実行（Docker Desktop 未起動） | 従来どおり。ローカル確認で代替 |

### Verified ✅

| 観点 | 結果 |
|---|---|
| A. 設計書 ↔ 設計書 | FR-E06 / OQ-E01 / DD-22 — `Composer` / `minutes` / `requirements` / `security` / `ui` 一致。新テーブルなし |
| B. DB ↔ 実装 | `event_attendances.household_id` + `REGISTERED` のみ。Flyway 追加なし |
| C. Security | `/calendar` は認証済み。参加フラグは自 household のみ。ADMIN は household なしで色分けしない |
| D. 環境 | ポート方針は従来どおり（PG `5433`、app `8081`） |
| E. 画面 ↔ Controller | `calendar.html`。凡例・本日参加は `showHouseholdParticipation`。参加中は緑、満員は赤（参加中優先） |
| F. テスト | `EventServiceTest.calendarHighlightsHouseholdParticipationAndListsToday`。`mvnw compile test-compile` 成功 |
| スコープ外 | FR-E05 履歴、他家庭の可視化、Mobile UI、FR-S04 |

---

# 30. Loop 14 — Mobile UI（承認済 2026-08-14）

## 目的

スマートフォンでカレンダー・参加登録・マイファミリーなどの主要操作を実用レベルにする（NFR-02）。

## 背景

- Loop 13 完了（PR #8）。カレンダーに参加色・本日一覧が載り、狭い画面での密度が上がった。
- MVP 成功基準「スマートフォンで主要操作が可能」は ⚠ のまま（基本レスポンシブのみ）。
- ヘッダーリンクが多く、折り返しで操作しづらい。カレンダーセルは高さ固定でタップしにくい。

## 候補

| 案 | 内容 | 評価 |
|---|---|---|
| **A. Mobile UI（推奨）** | CSS + ナビ折りたたみ。新テーブルなし | MVP 未達を埋める。Loop 13 の直後に効く |
| B. FR-S04 一括登録 | 複数イベントへの一括参加など | 「何を一括するか」が未定義。先に OQ が必要 |
| C. Testing / CI | CI で Testcontainers 実行、`testing.md` | 品質。利用者向けの変化は小さい |
| D. FR-E05 参加履歴 | 過去参加の一覧ページ | FR-E06 と近い。急がない |

## スコープ（Approved）

| 含む | 含まない |
|---|---|
| 狭い画面でのヘッダー折りたたみ | SPA / ネイティブアプリ |
| カレンダーのタップしやすい表示 | FR-S04 一括登録 |
| 既存画面の余白・フォント調整 | FR-E05 履歴ページ |
| 参加色・本日一覧は維持 | INSTRUCTOR ログイン、メール通知 |

## 承認が必要な事項

| ID | 質問 | 推奨案 |
|---|---|---|
| **OQ-M01** | Loop 14 の対象 | **A. Mobile UI** |
| **OQ-M02** | カレンダーの狭い画面 | **グリッド維持 + セル縮小**（アジェンダ切替は後続） |
| **OQ-M03** | ナビ | **ハンバーガー**（既存リンクを折りたたむ） |

## 承認記録

| 日付 | 内容 |
|---|---|
| 2026-08-14 | OQ-M01〜M03 を推奨案（案 A）のまま承認。 |

## 次アクション

実装完了。ローカル確認済。次アクション: commit / PR。

## 参照

- `Composer.md` §4.12
- `ui.md` §1〜2（スマホ対応は Loop 14 Approved）
- `requirements.md` NFR-02

---

# 30.1 Consistency Report — Loop 14（2026-08-14）

Consistency Engineer（`roles.md` §12）による横断確認。

| 区分 | 件数 |
|---|---|
| Blocker | 0 |
| Warning | 1（残） / 是正済 1 |

### Blockers

（なし）

### Warnings（是正済）

| ID | 内容 | 状態 |
|---|---|---|
| CON-L14-02 | UI ローカル確認 | ✅ **2026-08-14** 人間確認で解消 |

### Warnings（残）

| ID | 内容 | 扱い |
|---|---|---|
| CON-L14-01 | Testcontainers 未実行（Docker Desktop 未起動） | 従来どおり。ローカル確認で代替 |

### Verified ✅

| 観点 | 結果 |
|---|---|
| A. 設計書 ↔ 設計書 | OQ-M01〜M03 / NFR-02 — `Composer` / `minutes` / `requirements` / `ui` 一致。新テーブルなし |
| B. DB | 変更なし |
| C. Security | URL 変更なし。`/css/**` は permitAll。ナビは既存リンクの折りたたみのみ |
| D. 環境 | ポート方針は従来どおり |
| E. 画面 ↔ Controller | `fragments/header.html` に `nav-toggle`。`calendar.html` に `calendar-scroll`。JS なし |
| F. テスト | `CalendarRenderTest` が nav-toggle / calendar-scroll を含むことを検証 |
| スコープ外 | SPA、FR-S04、FR-E05、アジェンダ切替 |

---

# 31. Loop 15 — 一括参加登録（Approved 2026-08-15）

## 目的

同じスケジュールから作られた今後のイベントへ、自家庭の参加者を一度に登録する（FR-S04 最小）。

## 背景

- Loop 11 でスケジュールからイベント生成。Loop 12 で参加単位。Loop 13 でカレンダー色。Loop 14 でスマホ対応。
- PARENT は `/schedules` を見られない。一括の起点はイベント詳細が自然。
- 「毎週同じクラスに毎回登録し直す」負担を減らす。

## 候補

| 案 | 内容 | 評価 |
|---|---|---|
| **A. シリーズ一括参加（採用）** | S11 で「今後の同じスケジュールにも参加」 | 新テーブル不要。単位・定員の既存ルールを再利用 |
| B. FR-E05 参加履歴 | 過去参加の一覧ページ | カレンダーで月内は見える。急がない |
| C. Testing / CI | CI で Testcontainers、`testing.md` | 品質。利用者向けの変化は小さい |

## 確定スコープ（案 A）

| 含む | 含まない |
|---|---|
| `schedule_id` 付きイベント詳細からの一括登録 | CSV インポート |
| 開催日が今日以降の、同じ schedule のイベント | 過去イベント |
| 今登録した参加者と同じ（家庭 / その保護者 / その子ども） | ADMIN の他家庭代行 |
| 満員はその日だけスキップし、成功・スキップ件数を表示 | 手作りイベント（schedule なし） |
| 一括キャンセル | 複雑 RRULE |

## 承認事項

| ID | 質問 | 確定 | 状態 |
|---|---|---|---|
| **OQ-S03** | Loop 15 の対象 | **A. シリーズ一括参加** | ✅ Approved 2026-08-15 |
| **OQ-S04** | 起点画面 | **イベント詳細（S11）**（PARENT はスケジュール一覧を見ない） | ✅ Approved 2026-08-15 |
| **OQ-S05** | 満員の扱い | **その日だけスキップ**（全体失敗にしない） | ✅ Approved 2026-08-15 |
| **OQ-S06** | 一括キャンセル | **含める**（同じ参加者の今後分） | ✅ Approved 2026-08-15 |

## 実装メモ

- `POST /events/{id}/attend` に `scope=series`。新 URL・新テーブルなし。
- 対象は `schedule_id` 同一かつ開催日 ≥ 今日（Asia/Tokyo）。既登録はスキップ（件数に含めない）。
- フラッシュ例: 「2件に参加登録しました。満員などで 1件スキップしました。」

## 参照

- `Composer.md` §4.13
- `requirements.md` FR-S04

---

# 31.1 Consistency Report — Loop 15（2026-08-15）

Consistency Engineer（`roles.md` §12）による横断確認。

| 区分 | 件数 |
|---|---|
| Blocker | 0 |
| Warning | 1（是正済） |

### Blockers

（なし）

### Warnings（是正済）

| ID | 内容 | 状態 |
|---|---|---|
| CON-L15-01 | Testcontainers が Docker Engine 29 で API 1.32 を拒否され全スキップ | ✅ **2026-08-15** `src/test/resources/docker-java.properties` に `api.version=1.44`。`EventServiceTest` 16 件成功 |

### Verified ✅

| 観点 | 結果 |
|---|---|
| A. 設計書 ↔ 設計書 | OQ-S03〜S06 / FR-S04 — `Composer` / `minutes` / `requirements` / `security` / `ui` 一致。新テーブルなし |
| B. DB | 変更なし（既存 `events.schedule_id` / `event_attendances`） |
| C. Security | 新 URL なし。`POST /events/*/attend` は PARENT のみ。`scope=series` も自家庭のみ |
| D. 環境 | PG `5433` healthy、app `8081`、profile=`local` |
| E. 画面 ↔ Controller | `event/detail.html` に「今後の回にも参加」「今後の回もキャンセル」。手作りイベント（schedule なし）は非表示 |
| F. テスト | Testcontainers 実行可。Loop 15 の `EventServiceTest` 16 件成功。Security テストは `MinsukeUserDetails` を使う 3 件を修正済み |
| ローカル確認 | PARENT で `/events/7`（旗当番・schedule 付き）にシリーズボタン。`今後の回にも参加` → 「19件に参加登録しました。」`今後の回もキャンセル` → 「19件をキャンセルしました。」`/events/1`（手作り）はシリーズボタンなし。**2026-08-15 人間確認で問題なし** |
| スコープ外 | CSV、ADMIN 代行、FR-E05、Testing/CI |

---

# 32. Loop 16 — 参加履歴（Approved 2026-08-15）

## 目的

自家庭が参加した（する）イベントを、カレンダーの月をまたいで一覧できる（FR-E05 最小）。

## 背景

- Loop 13 でカレンダー色と「本日の参加」がある。月を切り替えると過去・先の参加は追いにくい。
- Loop 15 でシリーズ一括登録が増えると、「どの回に入っているか」を一覧したい。
- 新テーブルは不要。既存 `event_attendances`（household_id + REGISTERED）で足りる。

## 候補

| 案 | 内容 | 評価 |
|---|---|---|
| **A. PARENT の参加一覧ページ（採用）** | ナビから自家庭の参加を日付順に見る | カレンダーの穴を埋める。認可は my-family と同じ |
| B. 家族詳細に履歴を足す | ADMIN も他家庭の参加が見える | FR-F06 寄り。他家庭可視化は Loop 13 で出していない |
| C. Testing / CI | CI で Testcontainers、`testing.md` | 品質。画面は変わらない |

## 推奨スコープ（案 A）

| 含む | 含まない |
|---|---|
| PARENT 向け `/my-participations`（S24） | ADMIN の全家庭レポート |
| 自 household の **REGISTERED** | CANCELLED の履歴 |
| 今後と過去の両方（開催日の新しい順） | CSV / 集計グラフ |
| 日付・イベント名・参加者（家庭/保護者/子ども）・詳細へのリンク | FR-F06 利用状況ダッシュボード |
| ヘッダー（PARENT）に「参加履歴」 | ページネーション（件数が少ない前提。必要なら後続） |

## 承認事項

| ID | 質問 | 確定 | 状態 |
|---|---|---|---|
| **OQ-E02** | Loop 16 の対象 | **A. PARENT 自家庭の参加一覧** | ✅ Approved 2026-08-15 |
| **OQ-E03** | 出すレコード | **REGISTERED のみ**（キャンセルはイベント詳細で十分） | ✅ Approved 2026-08-15 |
| **OQ-E04** | 期間 | **今後＋過去**（開催日降順）。カレンダーは月内、本ページは横断 | ✅ Approved 2026-08-15 |
| **OQ-E05** | ADMIN | **出さない**（household なし。家族詳細への履歴は FR-F06） | ✅ Approved 2026-08-15 |

## 次アクション

**完了**（2026-08-16 人間 UI 確認）。続きの Loop は人間承認待ち。

## 参照

- `Composer.md` §4.14
- `requirements.md` FR-E05（FR-E06 とは別。カレンダー視認性 vs 横断一覧）

---

# 32.1 Consistency Report — Loop 16（2026-08-16）

Consistency Engineer（`roles.md` §12）による横断確認。ローカル UI は **2026-08-16 人間確認済**。

| 区分 | 件数 |
|---|---|
| Blocker | 0 |
| Warning | 0 |

### Blockers

（なし）

### Verified ✅

| 観点 | 結果 |
|---|---|
| A. 設計書 ↔ 設計書 | OQ-E02〜E05 / FR-E05 — `Composer` / `minutes` / `requirements` / `security` / `ui` / `roadmap` 一致。新テーブルなし |
| B. DB | 変更なし（既存 `event_attendances.household_id` + `REGISTERED`） |
| C. Security | `GET /my-participations` は `hasRole("PARENT")`。Service でも household 必須。ADMIN 403 / 未認証 302 |
| D. 環境 | 変更なし（PG `5433` / app `8081` / `local`） |
| E. 画面 ↔ Controller | `ParticipationController` → `event/participations.html`。ナビ「参加履歴」は PARENT のみ |
| F. テスト | `EventServiceTest` 19 件、`EventControllerSecurityTest` 11 件、`CalendarRenderTest` 2 件 — 失敗 0 |
| ローカル確認 | PARENT で `/my-participations`。ADMIN はナビなし・直アクセス 403。**2026-08-16 人間確認で問題なし** |

---

# 33. Future — 個人情報・決済・お知らせメールの外部化（Parked 2026-08-16）

人間相談（2026-08-16）。**Loop 16 では実装しない。** 後続 Loop で設計する。

## 動機

スクール運用を想定し、MinSuke 内に個人情報を持たせずニックネーム等で運用したい。本名・メール・連絡先は外部の会員システムに置き、登録家庭のサブスク決済とお知らせメール送信もそこで行う。

## 方針案（未承認）

| 置く場所 | 持つもの |
|---|---|
| **MinSuke** | 家庭 ID、ニックネーム、参加・カレンダー・お知らせ本文 |
| **外部（会員＋決済）** | 本名、住所、電話、メール、決済手段、サブスク状態 |

つなぎは **家庭 ID ↔ 外部会員 ID**。メールアドレスを MinSuke にコピーしない。

決済エンジンは MinSuke に作らない。課金単位の候補は **家庭（household）**。MinSuke が参照するのは `active` / `past_due` / `canceled` と外部 ID 程度。

お知らせメールは Loop 10 のアプリ内配信を残し、送信は外部に任せる。MinSuke は家庭 ID リスト＋本文だけ渡し、外部が登録メールへ送る。

完全な個人情報ゼロは不可（ログイン識別子・ニックネーム・参加履歴は個人情報になり得る）。狙いは「連絡先と決済を MinSuke に置かない」こと。

## 後続で決めること

| ID | 質問 | 候補 |
|---|---|---|
| **OQ-P01** | プロフィールをニックネーム中心にし、本名・連絡先を外部へ出すか | 案: MinSuke はニックネーム。外部が PII |
| **OQ-P02** | 登録家庭のサブスク決済をどこで行うか | 案: 外部課金。MinSuke は状態参照のみ |
| **OQ-P03** | お知らせメールの送信元 | 案: 外部の登録 email。MinSuke は SMTP を持たない（OQ-08 の将来分） |
| **OQ-P04** | ログイン | 外部 SSO か、当面 `email + password` のままか（後者はメールが MinSuke に残る） |
| **OQ-P05** | 未払い時に MinSuke で何を止めるか | ログイン / 参加登録 / 何もしない |

採用する場合、**PD-01**（MinSuke 内の個人情報を標準保持）の見直しが必要。

## 参照

- `requirements.md` OQ-P01〜P05 / OQ-08 / NFR-01 / PD-01
- `security.md` Open Questions

---

# 34. Loop 17 — ADMIN 参加状況（Approved 2026-08-17）

## 目的

管理者が、旗当番などのシリーズについて **各家庭の参加率** を把握し、あわせて月次のイベント充足を見られる（FR-E07 最小）。

## 背景

- Loop 16 は PARENT の自家庭一覧。ADMIN の全家庭レポートは出していない（OQ-E05）。
- イベント詳細は「その1回の参加者」だけ。当番の公平（誰が何回出たか）は見えない。
- 新テーブルは不要。`events.schedule_id` と `event_attendances` で足りる。

## 候補

| 案 | 内容 | 評価 |
|---|---|---|
| **A. ADMIN 参加状況＋家庭参加率（推奨）** | スケジュール別に家庭の参加率。月次の定員充足も出す | 旗当番の偏りが一覧で分かる |
| B. 家族詳細に履歴（FR-F06） | 1家庭ずつ | 全体比較ができない |
| C. Testing / CI | CI で Testcontainers | 画面は変わらない |

## 推奨スコープ（案 A）

**主:** スケジュール（例: 旗当番）の家庭参加率。

| 家庭 | 参加 | 対象 | 参加率 |
|---|---|---|---|
| C家 | 2 | 20 | 10% |
| A家 | 8 | 20 | 40% |

- **参加:** その回にその household の `REGISTERED` が **1件以上** なら 1 回（同じ回に保護者2人でも 1）
- **対象（分母）:** 期間内の当該 `schedule_id` の生成済みイベント数。満員で入れなかった回も含む
- **家庭:** **全家庭**（0% も出す。当番の穴を見つけるため）
- **並び:** 参加率の低い順
- **期間:** 初期は生成済みの **全期間**。今月フィルタあり
- **入口:** スケジュール詳細の「参加状況」、ヘッダー（ADMIN）「参加状況」

**副:** 対象月のイベント充足（日付・タイトル・定員・登録数・空き）。手作りイベント（`schedule_id` なし）もここに出す。シリーズ率は出さない。

| 含む | 含まない |
|---|---|
| ADMIN のみ `GET /admin/participations`（月次充足） | PARENT |
| `GET /schedules/{id}/participations`（家庭参加率） | CSV / グラフ |
| REGISTERED のみを「参加」 | CANCELLED の分析 |
| 全家庭（0% 含む） | 家族詳細の FR-F06 |
| | 未払いとの突合（OQ-P02） |

## 承認事項

| ID | 質問 | 確定 | 状態 |
|---|---|---|---|
| **OQ-R01** | Loop 17 の対象 | **A.** シリーズ家庭参加率を主、月次充足を副 | ✅ Approved 2026-08-17 |
| **OQ-R02** | 家庭の「1回参加」 | その回に household の REGISTERED が1件以上 | ✅ Approved 2026-08-17 |
| **OQ-R03** | 分母 | 期間内の生成済みイベント数（満員回も含む） | ✅ Approved 2026-08-17 |
| **OQ-R04** | 出す家庭 | **全家庭**（一度も登録していなくても 0%） | ✅ Approved 2026-08-17 |
| **OQ-R05** | 期間の初期値 | 当該スケジュールの **生成済み全期間**（今月フィルタあり） | ✅ Approved 2026-08-17 |
| **OQ-R06** | 月次充足表 | **含める**（手作りイベントの空き確認用） | ✅ Approved 2026-08-17 |

## 次アクション

**完了**（2026-08-17 人間 UI 確認）。続きの Loop は人間承認待ち。

## ローカル確認用アカウント（V10 入れ替え後）

| ロール | email | パスワード | 備考 |
|---|---|---|---|
| ADMIN | admin@minsuke.local | password | 参加状況・旗当番の家庭参加率 |
| PARENT | parent@minsuke.local | password | サンプル家（旗当番 高参加率） |
| PARENT | parent-b@minsuke.local | password | 中村家（中参加率） |
| PARENT | parent-c@minsuke.local | password | 佐藤家（0%） |

## 参照

- `Composer.md` §4.15
- `requirements.md` FR-E07（FR-E05 は PARENT、本機能は ADMIN）

---

# 34.1 Consistency Report — Loop 17（2026-08-17）

Consistency Engineer（`roles.md` §12）による横断確認。ローカル UI は **2026-08-17 人間確認済**。

| 区分 | 件数 |
|---|---|
| Blocker | 0 |
| Warning | 0 |

### Blockers

（なし）

### Verified ✅

| 観点 | 結果 |
|---|---|
| A. 設計書 ↔ 設計書 | OQ-R01〜R06 / FR-E07 — `Composer` / `minutes` / `requirements` / `security` / `ui` 一致。新テーブルなし |
| B. DB | スキーマ変更なし。local のみ `V10__reset_demo_seed.sql` でデモデータ入れ替え |
| C. Security | `/admin/**` と `/schedules/**` は ADMIN。Service でも ADMIN 必須 |
| D. 環境 | PG `5433` / app `8081` / `local`。V10 適用済 |
| E. 画面 ↔ Controller | `schedule/participations`・`event/admin-participations`。ナビ「参加状況」（ADMIN） |
| F. テスト | `AdminParticipationServiceTest` 4 件、`EventControllerSecurityTest` 14 件 — 失敗 0 |
| ローカル確認 | ADMIN で旗当番の家庭参加率（佐藤家 0%）。月次充足。PARENT は 403。**2026-08-17 人間確認で問題なし** |

---

# 15.2 MVP Integration Review — Loop 04〜07（2026-08-11）

Consistency Engineer 観点で横断確認。

| 区分 | 件数 |
|---|---|
| Blocker | 0 |
| Warning（是正済） | 3 |
| Warning（残） | 3 |

## MVP 成功基準 ↔ 実装

| 成功基準（requirements.md） | 結果 |
|---|---|
| 保護者が自家庭を登録し、本人・子どもを個別にイベント参加登録できる | ✅ `/register` + `/my-family` + `/events/{id}/attend` |
| ADMIN のみがイベントを作成できる | ✅ `SecurityConfig` + `EventService.requireAdmin` |
| 定員は参加者人数でカウントし超過を防止 | ✅ `EventCapacityFullException` + 満員表示 |
| スマートフォンで主要操作が可能 | ⚠ 基本レスポンシブあり。本格対応は Loop 11 |

## FR / 画面マトリクス

| ID | 要件 | 画面 | 結果 |
|---|---|---|---|
| FR-U01〜U03 | 登録・ログイン・ロール | S01〜S02 | ✅ |
| FR-F01〜F04 | 家庭 CRUD・一覧 | S04〜S09 | ✅ |
| FR-E01 | イベント作成（ADMIN） | S10 | ✅ |
| FR-E02 | 月間カレンダー | S03 | ✅ |
| FR-E03 | 個別参加登録・キャンセル | S11 | ✅ |
| FR-E04 | 定員チェック | S11 | ✅ |

## Loop 別完了確認

| Loop | 目的 | 状態 |
|---|---|---|
| 04 | プロジェクト基盤・Flyway・Docker・Health | ✅ |
| 05 | 認証・認可（BCrypt・CSRF・セッション） | ✅ |
| 06 | 家庭・保護者・子ども CRUD + household_id 認可 | ✅ |
| 07 | イベント・カレンダー・参加・定員 | ✅ |

## 横断 Verified

| 観点 | 結果 |
|---|---|
| A. 設計書 Loop 状態 | `minutes` / `Composer.md` / `roadmap` / `roles` — MVP 完了で同期 |
| B. Flyway V1 ↔ JPA | 6 テーブル（users〜event_attendances）一致 |
| C. Security ↔ URL | permitAll / ADMIN / PARENT と Controller 一致 |
| C. Service 認可 | `FamilyService` / `EventService` で role + household_id |
| D. 環境 | DB `5433`、アプリ `8081`（local）、example yml あり |
| E. 画面 S01〜S11 | Controller return ↔ templates 1:1 |
| E. ナビ | `fragments/header.html` — 実装 URL のみ |
| F. テスト | Auth / Family / Event / Calendar / Health — 9 クラス |

## Warnings

| ID | 内容 | 対応 |
|---|---|---|
| MVP-W01 | `SecurityConfig` に未実装 `/events/*/edit` | ✅ 削除（2026-08-11） |
| MVP-W02 | 未使用 `index.html`（Loop 04 表記） | ✅ 削除（`/` は `/calendar` へ redirect） |
| MVP-W03 | 未使用 `layout/main.html` | ✅ 削除 |
| MVP-W04 | `EventCapacityFullException` は Controller のみ捕捉 | 残（PRG で十分。汎用 handler は任意） |
| MVP-W05 | `error/forbidden.html` が handler の `message` 未表示 | 残（文言固定で可） |
| MVP-W06 | `docs/database/` と `src/.../db/migration/` の二重管理 | 残（変更時は両方更新） |

## 障害・是正履歴（MVP 期間）

| 事象 | Loop | 対応 |
|---|---|---|
| Flyway V2 checksum 不一致 | 05〜 | `LocalFlywayConfig` repair |
| シード BCrypt 不一致 | 05〜 | V2 ハッシュ修正 + 修復スクリプト |
| ログイン後エラー画面 | 07 | `calendar.html` の `th:classappend` 重複解消 |

## 結論

**MVP は設計・実装・ローカル検証の観点で完了。** 残 Warning は運用・次 Loop で問題にならない軽微事項。次は Loop 08（講師）または Mobile UI / テスト強化を選択。

---

# 24. Loop 08 — Instructor Management（設計 **Approved** 2026-08-12）

## 目的

スケジュール／イベント割当（Loop 09）と **講師稼働状況の可視化（将来必須）** の前提となる講師マスタを整備する。

## スコープ（Approved）

| 含む | 含まない（後続） |
|---|---|
| FR-I01 / FR-I01a（CRUD・一覧・詳細） | FR-I02 種目・資格 |
| `instructors` テーブル（孤立マスタ） | FR-I03 / FR-I06 稼働・割当 |
| `com.minsuke.instructor` | FR-I04 講師ログイン |
| ADMIN 変更、閲覧は認証済み全員 | FR-I05 イベントへの担当講師 |

## 人間承認結果（推奨案採用）

| ID | 決定 |
|---|---|
| OQ-I01 | **No** — Loop 08 で講師ログインしない |
| OQ-I02 | **認証済み全員** が一覧・詳細を閲覧可 |
| OQ-I03 | **区別しない**（同一マスタ） |
| OQ-I04 | **active 無効化を主**、未参照時のみ物理削除可 |
| DD-06 / DD-07 | **Approve** |
| DD-08 | イベント／スケジュールへの講師割当・稼働可視化は **Loop 09**（OQ-03 確定後） |

## 将来要件（ユーザー確認 2026-08-12）

- **イベントに担当講師を入れる** → Loop 09 で OQ-03 と一体設計（FR-I05）
- **担当講師の稼働状況がわかる** → 将来必須（FR-I06）。Loop 09 で指標・画面を具体化

## 設計サマリー

| 領域 | 内容 |
|---|---|
| DB | `docs/database/V3__create_instructors.sql` / `database.md` §12 |
| モジュール | AD-09: `instructor` パッケージ |
| URL | `/instructors`, `/new`, `/{id}`, `/{id}/edit`, `/{id}/delete` |
| 画面 | S13〜S16（`ui.md`） |
| 認可 | 変更 ADMIN / 閲覧 認証済み全員 |

## 実装チェックリスト

- [x] ADMIN: 講師追加・編集・無効化・削除
- [x] PARENT: 講師一覧・詳細（有効のみ）閲覧、作成画面は 403
- [x] ナビに「講師一覧」表示
- [x] Flyway V3 / V4 seed 適用（local）

## 実装概要

| レイヤ | 内容 |
|---|---|
| Flyway | `V3__create_instructors.sql` + local `V4__seed_instructors.sql` |
| モジュール | `com.minsuke.instructor` |
| 画面 | `instructor/list`, `detail`, `form` |
| 認可 | 変更 ADMIN / 閲覧 認証済み（PARENT は有効のみ） |

## 次アクション

**`feature/loop-08-instructor` → `main` への PR / merge**、または **Loop 09**（FR-I05 / FR-I06 / OQ-03）

## 参照

- `requirements.md` §6.4
- `database.md` §12
- `architecture.md` AD-09
- `security.md` Permission Matrix
- `ui.md` S13〜S16

---

# 23. Loop 07 — Event Management

## 実装概要

| 項目 | 内容 |
|---|---|
| FR-E01 | ADMIN のみイベント作成（`POST /events`） |
| FR-E02 | 月間カレンダー（`GET /calendar`） |
| FR-E03 | 保護者・子ども個別の参加登録・キャンセル（`POST /events/{id}/attend`） |
| FR-E04 | 定員チェック（参加者 1 名 = 定員 1）、満員表示 |

## 主要クラス

| レイヤ | クラス |
|---|---|
| Entity | `Event`, `EventAttendance` |
| Service | `EventService` |
| Controller | `EventController`, `CalendarController` |
| 画面 | `calendar.html`, `event/create.html`, `event/detail.html` |

## ローカル確認チェックリスト

- [x] ADMIN でイベント作成 → 詳細画面へ遷移
- [x] カレンダーにイベント表示・前月/翌月ナビ
- [x] PARENT で自家庭の保護者・子どもを個別参加登録
- [x] 定員 1 のイベントで 2 人目が登録不可（満員表示）
- [x] 参加キャンセル後に再登録可能

## 障害対応（2026-08-11）

| 事象 | 原因 | 対応 |
|---|---|---|
| ログイン後にエラー画面 | `calendar.html` の `th:classappend` 重複で Thymeleaf 500 | `th:class` に統合、`CalendarRenderTest` 追加 |

---

# 22. Loop 06 — Family Management

## 実装概要

| 項目 | 内容 |
|---|---|
| 家族一覧 | `GET /families` — 認証済み全員が閲覧（S04） |
| 家族詳細 | `GET /families/{id}` — 認証済み全員が閲覧（S05） |
| マイファミリー | `GET /my-family` — PARENT のみ（S06） |
| 家庭編集 | `GET/POST /my-family/edit` — PARENT 自家庭のみ（S07） |
| 保護者 CRUD | `/my-family/parents/...` — PARENT 自家庭のみ（S08） |
| 子ども CRUD | `/my-family/children/...` — PARENT 自家庭のみ（S09） |
| ADMIN 削除 | `POST /families/{id}/delete` — ユーザー未紐づけ家庭のみ |

## 認可方針

- Service 層で `household_id` を検証（PARENT は自家庭のみ変更可）
- ADMIN は全家庭を閲覧・削除可能（削除は `users` 未紐づけ時のみ）
- URL 層: `/my-family/**` → `hasRole('PARENT')`、`POST /families/*/delete` → `hasRole('ADMIN')`

## ローカル確認（2026-08-11 — ユーザー承認）

| 確認項目 | 結果 |
|---|---|
| PARENT — マイファミリー表示・家庭編集 | ✅ |
| PARENT — 保護者・子どもの追加・編集・削除 | ✅ |
| PARENT / ADMIN — 家族一覧・詳細閲覧 | ✅ |
| ADMIN — マイファミリー不可（403） | ✅ |

---

# 21. Loop 05 — 認証・認可

## 実装概要

| 項目 | 内容 |
|---|---|
| 認証 | Spring Security フォームログイン（email / password） |
| 登録 | 公開登録は PARENT のみ（Household + User 作成） |
| パスワード | BCrypt（SD-01） |
| CSRF | Spring Security 標準（有効） |
| セッション | 30 分無操作タイムアウト |
| ログイン後 | `/calendar` へリダイレクト |

## URL 認可

| パス | 認可 |
|---|---|
| `/login`, `/register`, `/css/**`, `/health` | 公開 |
| `/events/new`, `/events/*/edit` | ADMIN |
| `/my-family/**`, `/my-participations` | PARENT |
| その他 | 認証必須 |

## 起動・確認手順

```powershell
docker compose up -d
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

> **local プロファイルのポートは 8081**（`application-local.yml`。リポジトリ外・要手動作成）

| URL | 期待結果 |
|---|---|
| http://localhost:8081/ | `/calendar` へ（未ログイン時は `/login`） |
| http://localhost:8081/login | ログイン画面 |
| http://localhost:8081/register | 保護者登録画面 |
| http://localhost:8081/health | `{"status":"UP"}` |

**dev seed（local プロファイル・V10）:** `admin@minsuke.local` / `parent@minsuke.local` / `parent-b@minsuke.local` / `parent-c@minsuke.local` — パスワード `password`。旗当番の参加率確認用。

---

# 20. Loop 04 — 起動手順

```bash
# 1. PostgreSQL 起動
docker compose up -d

# 2. ローカル設定（初回のみ）
# cp src/main/resources/application-local.yml.example src/main/resources/application-local.yml

# 3. アプリ起動
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"

# 3. 確認
# http://localhost:8081/       — トップページ（local プロファイル）
# http://localhost:8081/health — {"status":"UP","application":"minsuke"}
```

**注意:** ホストの PostgreSQL が 5432 を使用している場合、Docker は **5433** にマップ（`application-local.yml` 参照）。

**環境（2026-08-11 確認）:**

| 項目 | バージョン |
|---|---|
| JDK | Temurin 21.0.12 |
| Docker | 29.7.2 |
| PostgreSQL（コンテナ） | 16-alpine |

**dev seed アカウント（local プロファイル・V10）:**

| ロール | email | パスワード（開発用） |
|---|---|---|
| ADMIN | admin@minsuke.local | password |
| PARENT | parent@minsuke.local | password（サンプル家・高参加率） |
| PARENT | parent-b@minsuke.local | password（中村家・中参加率） |
| PARENT | parent-c@minsuke.local | password（佐藤家・0%） |

---

# 18. Loop History

## Loop 11

- **Status:** **COMPLETED**
- **Started:** 2026-08-13
- **Completed:** 2026-08-14
- **Branch:** `feature/loop-11-schedule`（merged to main via PR #6）
- **Last Updated:** 2026-08-14 — main へ merge 済
- **Next Action:** —

## Loop 12

- **Status:** **COMPLETED**
- **Started:** 2026-08-14
- **Completed:** 2026-08-14
- **Branch:** `feature/loop-12-participation-unit`（merged to main via PR #7）
- **Last Updated:** 2026-08-14 — main へ merge 済
- **Next Action:** —

## Loop 13

- **Status:** **COMPLETED**
- **Started:** 2026-08-14
- **Completed:** 2026-08-14
- **Branch:** `feature/loop-13-my-participation`（merged to main via PR #8）
- **Last Updated:** 2026-08-14 — main へ merge 済
- **Next Action:** —

## Loop 14

- **Status:** **COMPLETED**
- **Started:** 2026-08-14
- **Completed:** 2026-08-14
- **Branch:** `feature/loop-14-mobile-ui`（merged to main via PR #9）
- **Last Updated:** 2026-08-14 — main へ merge 済
- **Next Action:** —

## Loop 17

- **Status:** **COMPLETED**
- **Started:** 2026-08-16
- **Completed:** 2026-08-17
- **Branch:** `feature/loop-17-admin-participation`
- **Last Updated:** 2026-08-17 — 人間 UI 確認済
- **Next Action:** PR → merge

## Loop 16

- **Status:** **COMPLETED**
- **Started:** 2026-08-15
- **Completed:** 2026-08-16
- **Branch:** `feature/loop-16-participation-history`（merged to main via PR **#12**）
- **Last Updated:** 2026-08-16 — main へ merge 済
- **Next Action:** —

## Loop 15

- **Status:** **COMPLETED**
- **Started:** 2026-08-14
- **Completed:** 2026-08-15
- **Branch:** `feature/loop-15-bulk-attend`（merged to main via PR **#10**）
- **Last Updated:** 2026-08-15 — main へ merge 済
- **Next Action:** —

## Loop 10

- **Status:** **COMPLETED**
- **Started:** 2026-08-13
- **Completed:** 2026-08-13
- **Branch:** `feature/loop-10-notification`（merged to main via PR #4）
- **Last Updated:** 2026-08-13 — main へ merge 済
- **Next Action:** —

## Loop 09

- **Status:** **COMPLETED**
- **Started:** 2026-08-12
- **Completed:** 2026-08-13
- **Branch:** `feature/loop-09-instructor-assignment`（merged to main via PR #2）
- **Last Updated:** 2026-08-13 — main へ merge 済
- **Next Action:** —

## Loop 08

- **Status:** **COMPLETED**
- **Started:** 2026-08-12
- **Completed:** 2026-08-12
- **Branch:** `feature/loop-08-instructor`（merged to main via PR #1）
- **Last Updated:** 2026-08-12 — main へ merge 済
- **Next Action:** —

## Loop 07

- **Status:** **COMPLETED**
- **Started:** 2026-08-11
- **Completed:** 2026-08-11
- **Last Updated:** 2026-08-11 — ローカル画面確認完了（ユーザー承認）、MVP 完了、統合レビュー済
- **Next Action:** **Loop 08 開始判断** — Instructor Management（または Mobile UI / Testing）

## Loop 06

- **Status:** **COMPLETED**
- **Started:** 2026-08-11
- **Completed:** 2026-08-11
- **Last Updated:** 2026-08-11 — ローカル画面確認完了（ユーザー承認）
- **Next Action:** **Loop 07 開始** — Event Management

## Loop 05

- **Status:** **COMPLETED**
- **Started:** 2026-08-11
- **Completed:** 2026-08-11
- **Last Updated:** 2026-08-11 — 認証・認可本格化、テスト追加
- **Next Action:** **Loop 06 開始** — Family Management

## Loop 04

- **Status:** **COMPLETED**
- **Started:** 2026-08-11
- **Completed:** 2026-08-11
- **Last Updated:** 2026-08-11 — JDK 21 / Docker 導入、Flyway・起動確認
- **Next Action:** **Loop 05 開始** — Authentication & Authorization

## Loop 03

- **Status:** **COMPLETED**
- **Started:** 2026-08-11
- **Completed:** 2026-08-11
- **Last Updated:** 2026-08-11 — DD-01〜05 ユーザー承認
- **Next Action:** **Loop 04 開始** — Project Foundation

## Loop 02

- **Status:** **COMPLETED**
- **Started:** 2026-08-11
- **Completed:** 2026-08-11
- **Last Updated:** 2026-08-11 — AD-04〜08 ユーザー承認
- **Next Action:** **Loop 03 開始** — Database Design

## Loop 01

- **Status:** **COMPLETED**
- **Started:** 2026-08-10
- **Completed:** 2026-08-11
- **Last Updated:** 2026-08-11 — OQ-05（選択肢 B）承認、Loop 01 完了
- **Next Action:** **Loop 02 開始** — Architecture & Technology Selection

---

# 20. Development Principle

```
正しい要件 → シンプルな設計 → 小さな実装 → テスト → レビュー → 記録 → 次のLoop
```
