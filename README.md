# MinSuke（みんスケ）

家庭・保護者・子ども・イベントを管理する **Greenfield** の Spring Boot アプリケーションです。

既存 MinSuke のソースは引き継がず、要件・設計から新たに実装しています（Loop Coding）。

**現状:** MVP（Loop 04〜07）完了・統合レビュー済（2026-08-11）

| 含む（MVP） | 含まない（Post-MVP） |
|---|---|
| 認証・認可（ADMIN / PARENT） | 講師管理（Loop 08） |
| 家庭・保護者・子ども CRUD | スケジュール管理 |
| イベント作成・カレンダー・個別参加・定員 | お知らせ・通知 |

---

## 技術スタック

| 項目 | 内容 |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.3 |
| UI | Thymeleaf（SSR） |
| DB | PostgreSQL 16 + Flyway + Spring Data JPA |
| Security | Spring Security（フォームログイン・CSRF・ロール） |
| ビルド | Maven Wrapper（`mvnw`） |

---

## 必要なもの

- JDK 21
- Docker Desktop（PostgreSQL 用）
- （任意）Docker が使える環境での Testcontainers テスト

---

## クイックスタート

### 1. DB 起動

```powershell
docker compose up -d
```

PostgreSQL はホストの **5433** にマップされます（`5433:5432`）。

### 2. ローカル設定

```powershell
copy src\main\resources\application-local.yml.example src\main\resources\application-local.yml
```

`application-local.yml` は `.gitignore` 対象です（リポジトリには含めません）。

### 3. アプリ起動

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

| URL | 内容 |
|---|---|
| http://localhost:8081/login | ログイン |
| http://localhost:8081/calendar | カレンダー（ホーム） |
| http://localhost:8081/health | ヘルスチェック |

### 4. 開発用アカウント（local プロファイルの seed）

| ロール | Email | Password |
|---|---|---|
| ADMIN | `admin@minsuke.local` | `password` |
| PARENT | `parent@minsuke.local` | `password` |

**開発専用です。本番や共有環境では使わないでください。**

---

## 主な機能（MVP）

- **認証:** 保護者登録、ログイン / ログアウト、ロール認可
- **家族:** 一覧・詳細、マイファミリー（保護者・子ども CRUD）
- **イベント:** ADMIN による作成、月間カレンダー、個別参加登録 / キャンセル、定員チェック

---

## プロジェクト構成（概要）

```
com.minsuke
├── auth/       … ユーザー・ログイン・登録
├── family/     … 家庭・保護者・子ども
├── event/      … イベント・カレンダー・参加
├── config/     … Security / WebMvc / Flyway
└── common/     … Health / 例外ハンドラ
```

設計・進捗の詳細は次のドキュメントを参照してください。

| 文書 | 内容 |
|---|---|
| [Composer.md](Composer.md) | Loop 方針・開発ルール |
| [roles.md](roles.md) | AI 専門家ロール（整合性チェック含む） |
| [minutes.md](minutes.md) | 進捗・意思決定・統合レビュー |
| [requirements.md](requirements.md) | 要件 |
| [architecture.md](architecture.md) | アーキテクチャ |
| [database.md](database.md) | DB 設計 |
| [security.md](security.md) | セキュリティ |
| [ui.md](ui.md) | 画面仕様 |
| [development-roadmap.md](development-roadmap.md) | Loop ロードマップ |

---

## テスト

```powershell
.\mvnw.cmd test
```

DB 連携テストは Testcontainers（PostgreSQL）を使用します。Docker 未起動時は該当テストがスキップされる場合があります。

---

## 開発の進め方

1. `main` … 公開・安定（MVP）
2. 機能は `feature/loop-XX-...` ブランチで実装 → PR → merge

完了: Loop 08〜14（PR #1 / #2 / #4 / #6 / #7 / #8 / **#9**）。  
次: **Loop 15 — 一括参加登録（実装・確認済、PR 待ち）**

---

## ライセンス

学習・個人開発用のプロジェクトです。利用条件を別途定める場合はここに追記します。
