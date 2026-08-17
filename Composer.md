# MinSuke — Project Instructions

## 1. Project Overview

**Project Name:** MinSuke（みんスケ）  
**Development Model:** Greenfield / New Development  
**Development Environment:** Cursor + Composer 2.5  
**Current Loop:** Loop 17 完了 — 次 Loop は人間承認待ち  
**Current Phase:** Post-MVP / 次 Loop 候補は Testing/CI または OQ-P01〜P05  
**Date:** 2026-08-17

MinSuke（みんスケ）は、家庭・講師・スケジュール・イベント等を管理するための新規システムとして開発する。

本プロジェクトでは、既存MinSukeのソースコードを引き継がない。

ただし、過去のMinSuke開発で得られた知見、必要機能、改善点、運用上の課題は、要件定義・設計時の参考情報として活用する。

---

## 2. Development Philosophy

MinSukeは以下の方針で開発する。

- 最初に要件を整理する
- 要件確定前に大規模な実装を開始しない
- シンプルで理解しやすい設計を優先する
- 将来の機能追加を考慮する
- セキュリティを後付けにしない
- テスト可能な構造にする
- 既存コードを持ち込まず、必要な設計を新しく検討する
- 必要以上に複雑な技術を採用しない
- 実装よりも正しい要件・設計を優先する

---

## 3. Loop Coding

MinSukeはLoop Coding方式で開発する。

基本的な流れ：

Requirements
    ↓
Design
    ↓
Implementation
    ↓
Test
    ↓
Review
    ↓
Record
    ↓
Next Loop

ただし、すべてのLoopで全工程を実施する必要はない。

各Loopの目的に応じて必要な工程だけを実施する。

---

## 4. Current Loop — Loop 01

Loop 01の目的は、

MinSukeの要件・業務フロー・システム構成・基本アーキテクチャを整理し、今後の開発方針を決定すること

である。

Loop 01ではコードを書かない

以下は禁止する。

Javaコードの実装
Spring Bootプロジェクト生成
DB構築
Entity実装
Controller実装
Service実装
Repository実装
HTML実装
CSS実装
JavaScript実装
認証機能実装
外部サービス連携
不要なライブラリ導入

必要なファイルや設計書は作成してよい。

---

## 4.1 Current Loop — Loop 02

Loop 02の目的は、

Loop 01 で承認された技術スタックの**バージョン・開発環境・プロジェクト構成・横断関心事**を確定し、Loop 04（実装開始）の準備を整えること

である。

Loop 02ではコードを書かない

以下は禁止する。

Javaコードの実装
Spring Bootプロジェクト生成
DB構築
Entity実装
Controller実装
Service実装
Repository実装
HTML実装
CSS実装
JavaScript実装
認証機能実装
外部サービス連携
不要なライブラリ導入

Loop 02で実施する。

- Java / Spring Boot バージョン確定
- Flyway / Docker Compose の採用判断
- Spring Security 導入タイミングの確定
- パッケージ・モジュール構成の詳細化
- 開発環境・設定管理方針
- `architecture.md` 等の設計書更新

---

## 4.2 Current Loop — Loop 03

Loop 03の目的は、

MVP に必要な **ER 図・テーブル定義・制約・インデックス・Flyway マイグレーション SQL** を設計し、Loop 04 での実装に引き渡すこと

である。

Loop 03ではコードを書かない

以下は禁止する。

Javaコードの実装
Spring Bootプロジェクト生成
DB構築（マイグレーション実行）
Entity実装
Controller実装
Service実装
Repository実装

Loop 03で実施する。

- ER 図の確定
- 全 MVP テーブルのカラム・制約定義
- FK / ON DELETE 方針
- 削除ポリシー（DQ-02）
- Flyway SQL 設計（`docs/database/`）
- `database.md` 更新

---

## 4.3 Current Loop — Loop 04

Loop 04の目的は、

承認済み設計に基づき **Spring Boot プロジェクト基盤** を構築し、Flyway・Docker Compose・Smoke Test で動作確認すること

である。

Loop 04で実施する。

- Spring Boot プロジェクト生成（Java, Maven）
- Flyway マイグレーション配置・適用
- Docker Compose（PostgreSQL 16）
- Spring Security 依存追加（`permitAll` プレースホルダ）
- 最小画面・ヘルスチェック
- Smoke Test

Loop 04では Entity / 業務 Controller は実装しない（Loop 05〜07）。

---

## 4.4 Current Loop — Loop 06

Loop 06の目的は、

**家庭（Household）・保護者（Parent）・子ども（Child）の CRUD** を実装し、認可（`household_id`）を Service 層で適用すること

である。

Loop 06で実施する。

- Parent / Child Entity + Repository
- FamilyService — household_id による認可
- 家族一覧（`/families`）・家族詳細（`/families/{id}`）
- マイファミリー（`/my-family/**`）— 保護者・子ども CRUD
- ADMIN による家庭削除（ユーザー未紐づけ時のみ）

---

## 4.5 Current Loop — Loop 07

Loop 07の目的は、

**イベント作成・カレンダー表示・個別参加登録・定員管理** を実装すること

である。

Loop 07で実施する。

- Event / EventAttendance Entity + Repository
- EventService（ADMIN 作成・月間カレンダー・参加登録/キャンセル・定員チェック）
- EventController / CalendarController
- Thymeleaf 画面（S03 カレンダー・S10 イベント作成・S11 イベント詳細）
- EventServiceTest / EventControllerSecurityTest

---

## 4.6 Current Loop — Loop 08

Loop 08の目的は、

**講師（Instructor）のマスタ管理** を設計・実装すること

である。

Loop 08で実施する。

**フェーズ A（設計）** ✅ 完了・承認（2026-08-12）

**フェーズ B（実装）** ✅ 完了（2026-08-12）— ローカル画面確認済

- Flyway V3 + `com.minsuke.instructor`
- ADMIN による講師 CRUD、認証済み全員の閲覧（PARENT は有効のみ）
- InstructorServiceTest / InstructorControllerSecurityTest
- ローカル動作確認 ✅

**Loop 09 へ持ち越し（Confirmed）** — → **Loop 09 開始**（下記 §4.7）

---

## 4.7 Current Loop — Loop 09

Loop 09の目的は、

**イベントへの担当講師設定（FR-I05）と、講師稼働状況の可視化（FR-I06）** を設計・実装すること

である。

Loop 09で実施する。

**フェーズ A（設計 — 現在）**

- **OQ-03**（スケジュールとイベントの関係）の確定案提示
- FR-I05 / FR-I06 のスコープ確定
- DB・画面・認可の設計案 → 人間承認

**フェーズ B（実装 — 承認後）**

- Flyway（events.instructor_id 等）
- イベント作成・詳細への担当講師
- 講師詳細での稼働（担当イベント一覧・集計）
- テスト・Consistency Review

**Loop 09 フェーズ A ではコードを書かない**（マイグレーション適用・Entity 変更は承認後）。

### Loop 09 推奨方針（Proposed）

| 項目 | 推奨 |
|---|---|
| OQ-03 | **イベント中心** — 独立 `schedules` テーブルは作らない。定期スケジュール（FR-S01）は後続 |
| FR-I05 | `events.instructor_id`（任意・単一講師） |
| FR-I06 | 割当イベントの集計・一覧（専用稼働テーブルは作らない） |
| FR-S01〜S04 | Loop 09 では本格スケジュール CRUD は対象外 |

---

## 4.8 Current Loop — Loop 10

Loop 10の目的は、

**アプリ内お知らせの作成・配信・既読管理（FR-N01〜N03）** を設計・実装すること

である。

Loop 10で実施する。

**フェーズ A（設計 — 現在）**

- OQ-08 / 配信対象 / 公開モデルの確定案提示
- FR-N01〜N03 のスコープ確定
- DB・画面・認可の設計案 → 人間承認

**フェーズ B（実装 — 承認後）**

- Flyway（`announcements` / `announcement_reads`）
- ADMIN 作成・認証済み閲覧・既読
- ヘッダー未読表示（任意）
- テスト・Consistency Review

**Loop 10 フェーズ A ではコードを書かない**（マイグレーション適用・Entity 変更は承認後）。

### Loop 10 推奨方針（Proposed）

| 項目 | 推奨 |
|---|---|
| OQ-08 | **アプリ内のみ**（メールは将来 Loop）— **Confirmed 2026-08-13** |
| 配信対象 | **認証済み全員**（ロールで絞らない） |
| 公開 | 作成と同時に公開（下書きなし） |
| 既読 | ユーザー単位（`announcement_reads`） |
| FR-S01 | Loop 10 でも対象外（スケジュール本格化は別 Loop） |

---

## 4.9 Current Loop — Loop 11

Loop 11の目的は、

**定期・単発スケジュールの本格管理（FR-S01）** を設計・実装すること

である。

Loop 11で実施する。

**フェーズ A（設計）** ✅ 2026-08-13 承認済

- **OQ-S01** / DD-14〜17 を推奨案のまま承認
- FR-S01 スコープ確定（FR-S03/S04 は後続）

**フェーズ B（実装）** ✅ 2026-08-14

- Flyway（`schedules` + `events.schedule_id`）✅
- スケジュール CRUD（ADMIN）✅
- WEEKLY 複数曜日（`schedule_weekdays` / DD-18）✅
- スケジュールからイベント生成（カレンダー連携）✅
- テスト・Consistency Review ← **必須**（`roles.md` §12）

**Loop 11 完了** ✅ 2026-08-14（PR #6 merge）

## 4.10 Current Loop — Loop 12

Loop 12の目的は、

**スケジュール／イベントごとの参加登録単位（FR-S03）** を設計・実装すること

である。

Loop 12で実施する。

**フェーズ A（設計）** ✅ 2026-08-14 承認済

- **OQ-S02** / DD-19〜21 を推奨案のまま承認

**フェーズ B（実装）** ✅ コード完了。Consistency Review 済。ローカル画面確認済（2026-08-14）。PR **#7** merge 済。

- Flyway V9（`schedules` / `events` の `participation_unit`、`event_attendances` 拡張）✅
- スケジュール／イベント作成・編集 UI ✅
- 参加登録 UI を単位に合わせて制限 ✅
- テスト（Testcontainers は Docker 未起動でスキップ）・Consistency Review ✅

### Loop 12 確定方針（Approved 2026-08-14）

| 項目 | 推奨 |
|---|---|
| OQ-S02 | **両方** — `schedules.participation_unit` を生成イベントへコピー。手作りイベントは `events.participation_unit` を直接設定 |
| 単位 | **HOUSEHOLD / PARENT / CHILD**（必須。新規は明示選択） |
| 既存イベント | `NULL` = 現行どおり保護者・子ども両方（後方互換） |
| HOUSEHOLD 参加 | `event_attendances.participant_type = HOUSEHOLD`（household_id のみ）。定員 1 家庭 = 1 |
| 生成コピー | スケジュールの単位をイベントへコピー（DD-17 と同様） |
| 含まない | FR-S04 一括登録、複雑 RRULE、Mobile UI、INSTRUCTOR ログイン |

### Loop 11 確定方針（Approved 2026-08-13）

| 項目 | 確定 |
|---|---|
| OQ-S01 | **テンプレート + インスタンス** — `schedules` マスタ + `events.schedule_id` |
| 繰り返し | MVP = **ONE_OFF + WEEKLY**（曜日 1=月…7=日、**複数指定可** — DD-18） |
| 生成 | ADMIN が「今後 N 週分を生成」（既定 4 週）。同一 schedule + 日付はスキップ |
| 講師 | スケジュールの `instructor_id` を生成イベントへコピー（FR-S02 の最小） |
| FR-S03/S04 | Loop 11 では対象外 |
| **FR-S03** | **参加登録単位（家庭/保護者/子ども）** — 後続 Loop（2026-08-13 要望） |

## 4.11 Current Loop — Loop 13

Loop 13の目的は、

**自家庭が参加しているイベントをカレンダーで識別すること（FR-E06）**

である。

**フェーズ A（設計）** ✅ 2026-08-14 承認済（OQ-E01 / DD-22）

**フェーズ B（実装）** ✅ コード完了。Consistency Review 済。ローカル画面確認済（2026-08-14）。PR **#8** merge 済。

### Loop 13 確定方針（Approved 2026-08-14）

| 項目 | 確定 |
|---|---|
| OQ-E01 | **両方** — カレンダーで参加中の色分け + 本日の参加一覧 |
| DD-22 | 自家庭の REGISTERED が 1 件以上なら参加中 |
| ADMIN | 色分け・本日参加は出さない |
| 含まない | 他家庭の可視化、FR-E05 履歴ページ、Mobile UI、FR-S04 |

## 4.12 Current Loop — Loop 14

Loop 14の目的は、

**スマートフォンでの主要操作を実用レベルにすること（NFR-02 / Mobile UI）**

である。

**フェーズ A（設計）** ✅ 2026-08-14 承認済（OQ-M01〜M03、案 A）

**フェーズ B（実装）** ✅ コード完了。Consistency Review 済。ローカル画面確認済（2026-08-14）。PR **#9** merge 済。

### Loop 14 候補と推奨

| 候補 | 内容 | 評価 |
|---|---|---|
| **A. Mobile UI（推奨）** | ナビ圧縮・カレンダーの狭い画面対応。新テーブルなし | MVP 成功基準「スマホで主要操作」が未達 |
| B. FR-S04 一括登録 | 複数イベントへの一括参加など | 要 OQ（何を一括するか未定義） |
| C. Testing / CI | Testcontainers を CI で実行、`testing.md` | 品質。画面の価値は増えない |
| D. FR-E05 参加履歴 | 過去の参加一覧ページ | FR-E06 と近いがカレンダー外 |

### Loop 14 確定方針（Approved 2026-08-14）

| 項目 | 推奨 |
|---|---|
| 対象 | 既存 Thymeleaf + CSS。SPA / ネイティブアプリは作らない |
| ナビ | 狭い画面では折りたたみ（ハンバーガー） |
| カレンダー | 狭い画面でもタップ可能なセル。参加色・本日一覧は維持 |
| 含まない | FR-S04、FR-E05、INSTRUCTOR ログイン、メール通知 |

## 4.13 Current Loop — Loop 15

Loop 15の目的は、

**同じスケジュールの今後イベントへ、自家庭を一括参加登録すること（FR-S04 最小）**

である。

**フェーズ A（設計）** ✅ 2026-08-15 承認済（OQ-S03〜S06、案 A）

**フェーズ B（実装）** ✅ コード完了。Consistency Review 済。ローカル画面確認済（2026-08-15 人間確認）。PR **#10** merge 済。

### Loop 15 候補と推奨

| 候補 | 内容 | 評価 |
|---|---|---|
| **A. FR-S04 シリーズ一括参加（推奨）** | イベント詳細から、同じ `schedule_id` の今後イベントへ同じ参加者を一括登録 | スケジュール＋参加単位の直後に効く。PARENT は `/schedules` を見ない |
| B. FR-E05 参加履歴 | 過去の参加一覧ページ | カレンダー色と近い。専用ページは後でもよい |
| C. Testing / CI | CI で Testcontainers、`testing.md` | 品質。画面は変わらない |

### Loop 15 確定方針（Approved 2026-08-15）

| 項目 | 確定 |
|---|---|
| 起点 | イベント詳細（S11）。`schedule_id` があるときだけ |
| 対象 | 開催日が今日以降（Asia/Tokyo）の、同じ schedule のイベント |
| 参加者 | 今選んだ単位と同じ（HOUSEHOLD / その保護者 / その子ども） |
| 定員満員 | その日だけスキップし、結果件数を出す（全体失敗にしない） |
| 一括キャンセル | 含める（同じ参加者の今後分） |
| 含まない | CSV、ADMIN の他家庭代行、過去イベント、手作りイベント（schedule なし） |

## 4.14 Current Loop — Loop 16

Loop 16の目的は、

**自家庭の参加イベントを、月をまたいで一覧すること（FR-E05 最小）**

である。

**フェーズ A（設計）** ✅ 2026-08-15 承認済（OQ-E02〜E05、案 A）

**フェーズ B（実装）** ✅ コード完了。Consistency Review 済。ローカル画面確認済（2026-08-16 人間確認）。PR **#12** merge 済。

### Loop 16 候補と推奨

| 候補 | 内容 | 評価 |
|---|---|---|
| **A. FR-E05 PARENT 参加一覧（採用）** | `/my-participations` で自家庭の REGISTERED を日付順に表示 | カレンダーは月内。シリーズ登録後の確認にも使える |
| B. 家族詳細に履歴 | ADMIN が他家庭の参加も見られる | Loop 13 で他家庭は出していない。FR-F06 寄り |
| C. Testing / CI | CI で Testcontainers、`testing.md` | 品質。画面は変わらない |

### Loop 16 確定方針（Approved 2026-08-15）

| 項目 | 推奨 |
|---|---|
| 対象者 | PARENT のみ（自 household） |
| レコード | REGISTERED のみ |
| 期間 | 今後＋過去（開催日降順） |
| 画面 | 日付・イベント名・参加者・詳細リンク。ナビに「参加履歴」 |
| 含まない | CSV、ADMIN 全家庭、キャンセル履歴、グラフ |

## 4.15 Current Loop — Loop 17

Loop 17の目的は、

**管理者がシリーズ（旗当番など）の各家庭の参加率と、月次のイベント充足を見ること（FR-E07 最小）**

である。

**フェーズ A（設計）** ✅ 2026-08-17 承認済（OQ-R01〜R06、案 A）

**フェーズ B（実装）** ✅ コード完了。Consistency Review 済。ローカル画面確認済（2026-08-17 人間確認）。PR **#13**。

### Loop 17 候補と推奨

| 候補 | 内容 | 評価 |
|---|---|---|
| **A. ADMIN 参加状況＋家庭参加率（採用）** | スケジュール別に各家庭の参加回数／対象回数／参加率。月次の定員充足も出す | 当番の公平に直結。新テーブルなし |
| B. 家族詳細に利用状況（FR-F06） | 1家庭ずつの履歴 | 全体の偏りが見えない |
| C. Testing / CI | CI で Testcontainers | 品質。画面は変わらない |

### Loop 17 確定方針（Approved 2026-08-17）

| 項目 | 確定 |
|---|---|
| 対象者 | ADMIN のみ |
| 主画面 | スケジュール（例: 旗当番）× 全家庭の参加率。低い順 |
| 参加の定義 | その回に household の REGISTERED が **1件以上** なら 1 回 |
| 分母 | 期間内の当該 `schedule_id` の生成済みイベント数（満員回も含む） |
| 期間 | 初期は生成済みの全期間。今月フィルタあり |
| 副画面 | 対象月のイベント充足（定員・登録数・空き） |
| 含まない | CSV、グラフ、PARENT、CANCELLED 分析、家族詳細の FR-F06、未払い突合 |

## 5. Loop 01 Investigation

Loop 01では、以下を検討する。

5.1 Target Users

想定ユーザーを整理する。

例：

管理者
一般家庭・保護者
講師
その他必要なユーザー

実際に必要なユーザー種別は調査・設計結果から決定する。

5.2 Business Requirements

以下を整理する。

MinSukeで解決したい課題
利用者
利用目的
業務フロー
必要な情報
必要な操作
管理者が行う作業
一般ユーザーが行う作業
講師が行う作業

---

## 6. Candidate Features

以下は現時点での候補である。

これらをそのまま採用するのではなく、必要性・依存関係・優先順位を評価する。

Family Management
家庭情報
保護者
子ども
家族構成
連絡先
利用状況
Instructor Management
講師情報
担当種目
資格
担当スケジュール
講師の稼働状況
Schedule Management
スケジュール
講師割当
家庭・子どもの参加
カレンダー
一括登録
Event Management
イベント作成
開催日時
対象者
参加管理
イベント履歴
Notification
お知らせ作成
配信対象
配信日時
配信履歴
既読管理
User / Role Management
管理者
一般ユーザー
講師
権限管理
Mobile UI
スマートフォン対応
レスポンシブデザイン
モバイルナビゲーション
操作性改善

---

## 7. Requirements Rules

要件を整理するときは、以下を守る。

Do not assume

ユーザーから明示されていない仕様を確定事項として扱わない。

Separate facts and proposals

以下を明確に分ける。

Confirmed Requirement
Proposed Requirement
Open Question
Future Consideration
Avoid overengineering

必要性が確認できていない機能を追加しない。

Prioritize MVP

最初からすべての機能を実装しようとしない。

---

## 8. Architecture Investigation

Loop 01では、以下を比較検討する。

Backend

候補：

Java
Spring Boot
Frontend

候補：

Thymeleaf
React
その他必要な構成

既存技術を自動的に踏襲せず、MinSukeの目的に最も適した構成を検討する。

---

## 9. Database Design

Loop 01ではDBの基本設計方針を検討する。

以下を整理する。

DB候補
Entity候補
テーブル候補
主キー
外部キー
リレーション
制約
インデックス
履歴管理
将来拡張性

ただし、Loop 01ではDBを実際に構築しない。

---

## 10. Security

セキュリティを設計段階から考慮する。

最低限、以下を検討する。

Authentication
Authorization
Role
Password management
Session management
CSRF
Input validation
Access control
Logging
Audit trail
Sensitive information
Backup
Data protection

---

## 11. Testing Strategy

Loop 01ではテスト戦略を設計する。

検討対象：

Unit Test
Integration Test
Controller Test
Repository Test
Security Test
End-to-End Test

テストを後付けにせず、開発初期から考慮する。

---

## 12. Documentation

Loop 01では必要に応じて以下の設計書を作成する。

requirements.md
architecture.md
database.md
security.md
ui.md
development-roadmap.md

ただし、必要性がないファイルを形式的に作成する必要はない。

---

## 13. Documentation Principles

設計書は、

簡潔
具体的
更新可能
相互矛盾がない
実装と追跡可能

であることを重視する。

仕様変更が発生した場合は、関連文書を更新する。

---

## 14. Decision Making

技術選定では以下を優先する。

要件適合性
安全性
保守性
テスト容易性
拡張性
開発コスト
学習コスト

単に「新しい技術だから」という理由で採用しない。

---

## 15. Evidence-Based Reasoning

重要な設計判断については、可能な限り以下を記録する。

Decision
Reason
Alternative
Why rejected
Impact

---

## 16. Human Approval Boundary

以下の事項は、勝手に確定しない。

最終的な業務要件
ユーザー権限
個人情報の扱い
外部サービス
本番環境
課金サービス
データ保持期間
重要なセキュリティポリシー

不明な場合はOpen Questionとして記録する。

---

## 17. Future Loop

Loop 01終了後、次のLoopを決定する。

例（初期ロードマップ案 — 実際の実行順は下記と異なる）:

Loop 02 — Architecture & Technology Selection
Loop 03 — Database Design
Loop 04 — Project Foundation
Loop 05 — Authentication & Authorization
Loop 06 — Family Management
Loop 07 — Event Management
Loop 08 — Instructor Management
Loop 09 — Schedule Management（初期案名。実施は Instructor Assignment）
Loop 10 — Notification
Loop 11 — Mobile UI（初期案名。**現行 Loop 11 は Schedule Management**）
Loop 12 — Testing & Security
Loop 13 — Integration Review

**現行確定順序（2026-08-17）:** Loop 08〜**17** 完了。次 Loop は人間承認待ち（Testing/CI、または個人情報・決済・メール外部化 OQ-P01〜P05）。

実際の順番は Loop 01 の結果から決定する。上記が現行の確定順序である。

---

## 18. Loop Completion Criteria

Loop 01は以下を満たした場合に完了とする。

 プロジェクト目的が明確
 Target Userが整理されている
 主要業務フローが整理されている
 MVP候補が整理されている
 将来機能が整理されている
 技術候補が整理されている
 アーキテクチャ方針が整理されている
 DB方針が整理されている
 セキュリティ方針が整理されている
 テスト方針が整理されている
 Open Questionsが整理されている
 次のLoopが決定されている
 minutes.md が更新されている

---

## 19. Important Rule

Loop 01の成功条件は「コードができること」ではない。

成功条件は、

何を作るのか、誰のために作るのか、どこまで作るのか、どのような構造にするのかが明確になっていること

である。