# MinSuke — Testing（Loop 20）

**Status:** Completed（PR **#15** merge 2026-08-23）  
**目的:** 地域スケジュール管理アプリとしての品質ゲート。画面機能の追加ではない。

---

## 1. 方針

| 項目 | 内容 |
|---|---|
| ランナー | GitHub Actions（`ubuntu-latest`、JDK 21、Maven Wrapper） |
| トリガー | `main` への push、すべての pull_request |
| 統合テスト | Testcontainers PostgreSQL 16（本番と同じエンジン。H2 は使わない） |
| ローカル Docker なし | `@Testcontainers(disabledWithoutDocker = true)` により DB テストはスキップ。`mvnw test` は成功しうる |
GitHub Actions の JVM 既定は UTC。Hibernate が `TIME` をタイムゾーン変換すると 14:00〜15:00 が 23:00〜00:00 になり `chk_events_time_range` に落ちる。CI は `TZ=Asia/Tokyo`、Surefire は `-Duser.timezone=Asia/Tokyo`。`LocalTime` は `@JdbcTypeCode(SqlTypes.TIME)` で素の TIME として保存する。
| Docker Engine 29 | `src/test/resources/docker-java.properties` の `api.version=1.44` を維持する |

E2E ブラウザテスト・カバレッジ失敗ゲートは本 Loop に含めない。

---

## 2. ローカル

```powershell
.\mvnw.cmd test
```

Docker Desktop が動いていると Testcontainers が実行される。

---

## 3. テストの置き場所

| 種類 | 例 |
|---|---|
| Service + DB | `*ServiceTest`（`@SpringBootTest` + Testcontainers） |
| 認可・公開 URL | `*ControllerSecurityTest` / `HealthControllerTest` |
| カレンダー描画 | `CalendarRenderTest` |

新機能を足すときは、成功パスと拒否パス（権限・定員・サブスク）の少なくとも一方をテストする。

---

## 4. 本 Loop で足したもの

- `.github/workflows/ci.yml`
- 非 ACTIVE 家庭は参加登録できないこと（`EventServiceTest`）
