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
**Loop 08 / 09 / 10 完了** — PR #1 / #2 / #4 を `main` へ merge 済  
**Current Loop:** Loop 11 — Schedule Management（`feature/loop-11-schedule`）

## Date

**2026-08-13**（最終更新）

---

# 1. Project Status

## Current State

**Loop 11 設計中**（2026-08-13）。FR-S01（スケジュール本格化）の草案を提示。人間承認待ち。

## Loop 11 Progress

| 作業 | 状態 |
|---|---|
| ブランチ作成 `feature/loop-11-schedule` | ✅ |
| FR-S01 / OQ-S01 設計草案 | ✅ |
| 人間承認 | ⏳ **承認待ち** |
| Flyway V7 + スケジュール UI | ⏳ 承認後 |
| イベント生成 | ⏳ 承認後 |
| テスト・ローカル確認 | ⏳ |
| Loop 11 完了 | ⏳ |

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

**Loop 08 / 09 / 10** merged。  
**Loop 11 — Schedule Management**（ブランチ: `feature/loop-11-schedule`）  
設計中。次アクション: OQ-S01 / DD-14〜17 の人間承認 → 実装。

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
**Loop 11 設計中。** 次アクション: OQ-S01 / DD-14〜17 の人間承認 → 実装。

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
| **DD-16** | `events.schedule_id` NULL FK、ON DELETE SET NULL | **Approve** |
| **DD-17** | 生成イベントへ schedule の講師・定員等をコピー | **Approve** |
| **生成単位** | デフォルト 4 週、ADMIN が実行 | **Approve** |

## 設計サマリー

| 領域 | 案 |
|---|---|
| DB | `docs/database/V7__create_schedules.sql` |
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

人間承認 → 実装フェーズ

## 参照

- `Composer.md` §4.9
- `requirements.md` §6.3 Loop 11
- `database.md` §17

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
| `/my-family/**` | PARENT |
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

**dev seed（local プロファイル）:** `admin@minsuke.local` / `parent@minsuke.local` — パスワード `password`

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

**dev seed アカウント（local プロファイル）:**

| ロール | email | パスワード（開発用） |
|---|---|---|
| ADMIN | admin@minsuke.local | password |
| PARENT | parent@minsuke.local | password |

---

# 18. Loop History

## Loop 11

- **Status:** **IN PROGRESS**（設計）
- **Started:** 2026-08-13
- **Branch:** `feature/loop-11-schedule`
- **Last Updated:** 2026-08-13 — 設計草案・承認待ち
- **Next Action:** **人間承認**（OQ-S01 / DD-14〜17）→ 実装

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
