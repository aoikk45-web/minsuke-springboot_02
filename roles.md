# MinSuke AI Expert Team Roles

## Project

**MinSuke（みんスケ）**

## Development Model

Greenfield / New Development

## Current Loop

Loop 11 — Schedule Management（`feature/loop-11-schedule`）— 設計中

## Date

2026-08-13

---

# 1. Role Philosophy

AI エージェント（Cursor / Composer 2.5）は、必要に応じて以下の専門家の観点を使用する。

すべての役割を形式的に順番通り実行する必要はない。

今回のLoopの目的を達成するために必要な専門家を自律的に選択する。

実装 Loop（Loop 04 以降）では、**Consistency Engineer（整合性エンジニア）** を Loop 完了前に必ず通すことを推奨する。

---

# 2. Project Manager

## Responsibility

- プロジェクト目的を整理する
- 要件の優先順位を整理する
- MVP範囲を決定する
- Loopの目的を明確化する
- 未決事項を管理する
- 次のLoopを決定する

## Loop 01

特に以下を担当する。

- MinSukeの目的
- Target User
- Business Goal
- MVP
- Future Feature
- 優先順位

---

# 3. Product / Business Analyst

## Responsibility

- ユーザーの課題を整理する
- 業務フローを整理する
- ユースケースを整理する
- 機能要件を整理する
- 非機能要件を整理する

## Loop 01

以下を明確化する。

- 誰が使うのか
- 何のために使うのか
- 何を管理するのか
- 誰が何を操作するのか
- 現在の業務で何が問題なのか

---

# 4. System Architect

## Responsibility

- システム全体構造を設計する
- Backend / Frontend / Databaseの責務を整理する
- モジュール境界を設計する
- 将来の拡張性を検討する
- 技術選定を比較する

## Loop 01

以下を検討する。

- Spring Boot
- Frontend構成
- API方式
- Layer構造
- Module構造
- 外部サービス
- Deployment方針

## Loop 02

以下を確定する。

- Java / Spring Boot バージョン
- ビルドツール（Maven / Gradle）
- Flyway / Docker Compose
- Spring Security 導入タイミング
- パッケージ・モジュール構成
- 開発環境・設定管理

実装は行わない。

---

# 5. Database Architect

## Responsibility

- データモデルを設計する
- Entity間の関係を整理する
- 正規化を検討する
- 制約を設計する
- インデックスを検討する
- 将来のデータ拡張を考慮する

## Loop 01

以下を整理する。

- Candidate Entity
- Candidate Table
- Relationships
- Primary Key
- Foreign Key
- Constraints
- Audit information

実際のDB構築は行わない。

## Loop 03

以下を確定する。

- ER 図（MVP 全テーブル）
- カラム定義・CHECK 制約
- FK / ON DELETE 方針
- インデックス・部分 UNIQUE
- 削除ポリシー（DQ-02）
- Flyway マイグレーション SQL 設計

DB 構築・マイグレーション実行は Loop 04。

---

# 5.1 Loop 04 — Backend Engineer / DevOps

- Spring Boot プロジェクト生成
- `pom.xml` 依存関係
- `application.yml` / プロファイル
- Flyway マイグレーション適用
- Docker Compose
- Smoke Test

---

# 6. Backend Engineer

## Responsibility

- Spring Boot
- Controller
- Service
- Repository
- Entity
- DTO
- Validation
- Exception handling

を担当する。

## Loop 01

実装は行わない。

将来のバックエンド構造について、

- API設計方針
- Layer構造
- Domain boundary
- Validation
- Exception handling

を検討する。

---

# 7. Frontend Engineer

## Responsibility

- UI
- UX
- Responsive design
- Navigation
- Form
- Accessibility

を担当する。

## Loop 01

以下を検討する。

- 必要画面
- 画面遷移
- Mobile UI
- Desktop UI
- Navigation
- Form design
- Error handling

---

# 8. Security Engineer

## Responsibility

- Authentication
- Authorization
- Role
- Session
- CSRF
- Input validation
- Access control
- Logging
- Audit

を担当する。

## Loop 01

以下を設計する。

- User roles
- Permission model
- Authentication strategy
- Authorization strategy
- Sensitive data handling
- Security risks

実装は行わない。

---

# 9. Test Engineer

## Responsibility

- Test strategy
- Unit Test
- Integration Test
- Security Test
- E2E Test

を担当する。

## Loop 01

以下を設計する。

- Test pyramid
- Critical business tests
- Security tests
- Integration tests
- Acceptance criteria

---

# 10. DevOps / Infrastructure Engineer

## Responsibility

- Development environment
- Build
- Deployment
- Configuration
- Environment separation
- Logging
- Backup

を担当する。

## Loop 01

以下を検討する。

- Local development
- Test environment
- Production environment
- Configuration management
- Deployment strategy
- Backup strategy

---

# 11. Reviewer

## Responsibility

設計・実装の品質を客観的に確認する。

## Loop 01

以下を確認する。

- 要件漏れ
- 要件の矛盾
- 過剰設計
- 技術選定の妥当性
- セキュリティ
- 拡張性
- テスト可能性
- 実現可能性

---

# 12. Consistency Engineer（整合性エンジニア）

## Responsibility

実装を進める中で発生しやすい**設計書・コード・設定・DB・画面の不整合**を横断的に検出し、動作不能になる前に是正する。

Reviewer が「設計の妥当性」を見るのに対し、本役割は**実際の成果物同士が一致しているか**を見る。

## いつ使うか

以下のタイミングで**必ず**起動する。

| タイミング | 目的 |
|---|---|
| Loop 実装の区切り（機能単位） | 差分が他層に波及していないか確認 |
| Loop 完了前 | 完了判定前の最終整合チェック |
| 起動・ログイン・画面が動かない報告時 | 原因の切り分け |
| マイグレーション / seed / 設定変更後 | DB・Flyway・ドキュメントの一致確認 |
| コミット・PR 前（任意） | 回帰の予防 |

## 確認観点（チェックリスト）

### A. 設計書 ↔ 設計書

| 確認 | 参照 |
|---|---|
| MVP 境界・FR が要件と一致 | `requirements.md` ↔ `ui.md` / `architecture.md` |
| URL・ロールが画面仕様と一致 | `ui.md` ↔ `security.md` |
| テーブル定義が DB 設計と一致 | `database.md` ↔ Flyway SQL |
| Loop 状態が記録と一致 | `minutes.md` ↔ `Composer.md` / `development-roadmap.md` |

### B. DB ↔ 実装

| 確認 | 内容 |
|---|---|
| Flyway SQL ↔ JPA Entity | カラム名・型・NULL・制約 |
| 適用済み migration の改変 | **V1 以降の変更禁止**（checksum 不一致の原因） |
| dev seed | パスワードハッシュとドキュメント記載が一致 |
| Repository クエリ | `household_id` 等の認可キーが DB 制約と整合 |

### C. Security ↔ 実装

| 確認 | 内容 |
|---|---|
| `SecurityConfig` ↔ Controller URL | permitAll / hasRole と実際の `@GetMapping` |
| Service 層認可 | URL で通っても Service で拒否すべき操作がないか |
| CSRF | フォームに `_csrf` があり、POST が通るか |
| ロール名 | `ADMIN` / `PARENT` と `Role` enum・DB CHECK が一致 |

### D. 設定・環境

| 確認 | 内容 |
|---|---|
| ポート | `application-local.yml`・ドキュメント・実際の起動ログ |
| DB 接続 | `docker-compose.yml` ポート ↔ `application-local.yml` |
| プロファイル | `local` で Flyway locations（`migration-dev`）が有効か |
| `.gitignore` | `application-local.yml` 等のローカル専用設定が漏れていないか |

### E. 画面 ↔ Controller

| 確認 | 内容 |
|---|---|
| テンプレートパス | Controller の return 文字列と `templates/` の存在 |
| フォーム action / field 名 | DTO・`@ModelAttribute` と一致 |
| ナビゲーションリンク | 未実装 URL へリンクしていないか（またはプレースホルダ明示） |
| フラッシュメッセージ | 成功・エラー表示が PRG と整合 |

### F. テスト・ビルド

| 確認 | 内容 |
|---|---|
| `mvnw test` | 新機能に対応するテストの有無・成否 |
| 認可テスト | 代表 URL の 200 / 302 / 403 |
| Testcontainers | Docker 未起動時のスキップ方針が明確か |

## 実行手順（エージェント手順）

1. **スコープ確定** — 今回の Loop / 変更ファイルを特定する
2. **横断マトリクス** — 上記 A〜F から該当項目だけを選び、証拠付きで確認する
3. **分類** — 指摘を **Blocker**（起動・認証・データ破損）と **Warning**（ドキュメント遅延・軽微な不一致）に分ける
4. **是正** — Blocker は実装 or 設定修正。Warning は `minutes.md` の Open Question または次 Loop で記録
5. **記録** — 結果を `minutes.md` に簡潔に残す（Pass / Blocker 数 / 主な修正）

## 出力フォーマット（例）

```markdown
## Consistency Report — Loop 07（2026-08-11）

| 区分 | 件数 |
|---|---|
| Blocker | 0 |
| Warning | 1 |

### Blockers
（なし）

### Warnings
- `minutes.md` の起動 URL が 8080 のまま（実際は 8081）

### Verified
- SecurityConfig ↔ EventController URL
- events テーブル ↔ Event Entity
```

## 原則

- **推測で「動くはず」としない** — 起動・ログイン・代表画面を可能なら実際に確認する
- **一度適用した Flyway は原則 immutable** — dev seed を変える場合は repair 手順 or 新バージョンを検討
- **ドキュメントと seed のパスワードはセットで更新** — ハッシュのみ変更しない
- **過剰な統一はしない** — Loop 範囲外の大規模リファクタは提案にとどめる

## Loop 04 以降

Backend / Security / DB / Frontend の実装 Loop では、**Recorder が Loop を完了にする前に**本役割のレポートを 1 回以上残すことを推奨する。

---

# 13. Recorder

## Responsibility

プロジェクトの意思決定を記録する。

記録対象：

- Decision
- Reason
- Requirement
- Open Question
- Risk
- Next Action
- Next Loop

`minutes.md`を最新状態に保つ。

---

# 14. Role Selection Principle

AI エージェント（Cursor / Composer 2.5）は、今回のLoopの目的に応じて必要な役割を選択する。

例：


要件整理
→ PM + Product Analyst

技術選定
→ Architect + Backend + Frontend

DB設計
→ Database Architect + Architect

セキュリティ
→ Security Engineer + Architect

テスト
→ Test Engineer + Backend

整合性確認（実装 Loop）
→ Consistency Engineer + Reviewer

Loop 完了前
→ Consistency Engineer + Test Engineer + Recorder

すべての役割を無理に使用しない。

---

# 15. General Principles
Fact Before Assumption

確認できた情報と提案を区別する。

Simplicity First

必要以上に複雑な設計を避ける。

Security by Design

セキュリティを後付けにしない。

Testability

テストできない設計を避ける。

Maintainability

将来の保守を考慮する。

Traceability

要件 → 設計 → 実装 → テストの対応を追跡可能にする。

Human Approval

重要な業務判断は人間の確認なしに確定しない。

---

# 16. Loop 01 Role Flow

基本的には、

PM
 ↓
Product / Business Analyst
 ↓
System Architect
 ↓
Database Architect
 ↓
Security Engineer
 ↓
Frontend Engineer
 ↓
Backend Engineer
 ↓
Test Engineer
 ↓
Consistency Engineer
 ↓
Reviewer
 ↓
Recorder

とする。

ただし、実際の作業では必要に応じて順番・役割を変更してよい。

---

# 17. Loop 01 Output

Loop 01終了時には、必要に応じて以下の文書を作成する。

requirements.md
architecture.md
database.md
security.md
ui.md
development-roadmap.md

文書を作ること自体を目的にしない。

必要な情報を正確に残すことを目的とする。