  # GF2Tool - ゲーム情報ウィキプラットフォーム

  リンク: https://github.com/kkanyo/GF2Tool

  > モダンなJava/Reactスタックを活用したフルスタックウェブアプリケーション ポートフォリオプロジェクト

  **[한국어](README.md) | [English](README_EN.md)**

  ## 📋 プロジェクト概要

  **GF2Tool**は、スマホゲーム「ドールズフロントライン2：エクシリウム」に関するデータ情報を効率的に管理・提供するためのウェブプラットフォームです。
  Spring Boot 3.5.11とReact 19をベースにした最新のフルスタックプロジェクトで、マイクロサービスアーキテクチャの原則とベストプラクティスを適用しています。

  ## 🛠 技術スタック

  | レイヤー             | 技術                                |
  | -------------------- | ----------------------------------- |
  | **ランタイム**       | Java 21, Spring Boot 3.5.11         |
  | **ビルドツール**     | Gradle                              |
  | **データベース**     | MySQL 8.0, JPA, QueryDSL 5.0.0      |
  | **フロントエンド**   | React 19.2.0, TypeScript 5.9.3      |
  | **バンドラー**       | Vite 7.2.4                          |
  | **スタイリング**     | Tailwind CSS 4.2.1                  |
  | **ルーティング**     | React Router 7.12.0                 |
  | **HTTPクライアント** | Axios                               |
  | **国際化**           | i18next, react-i18next (en, ja, ko) |
  | **API文書化**        | Springdoc OpenAPI (Swagger UI)      |
  | **テスト**           | JUnit 5, H2, AssertJ                |
  | **コード品質**       | ESLint, Prettier, TypeScript strict |
  | **インフラ**         | Docker & Docker Compose             |

  ## 📁 プロジェクト構造

  ```
  GF2Tool-back/
  ├── backend/                      # Spring Bootバックエンド
  │   ├── src/
  │   │   ├── main/java/com/kkanyo/gf2tool/
  │   │   │   ├── domain/          # ビジネスロジック (DDD)
  │   │   │   ├── global/          # グローバル設定、例外処理
  │   │   │   └── Gf2toolApplication.java
  │   │   ├── main/resources/
  │   │   │   ├── application.yml
  │   │   │   ├── application-local.yml
  │   │   │   └── templates/
  │   │   └── test/java/           # ユニット・統合テスト
  │   ├── build.gradle             # Gradle設定
  │   ├── gradle/wrapper/          # Gradle Wrapper
  │   └── HELP.md
  │
  ├── frontend/                     # React/TypeScriptフロントエンド
  │   ├── src/
  │   │   ├── components/
  │   │   │   ├── common/          # 共通コンポーネント
  │   │   │   └── layout/          # レイアウトコンポーネント
  │   │   ├── pages/
  │   │   │   └── doll/            # ドメイン特化ページ
  │   │   ├── api/                 # AxiosおよびAPIクライアント
  │   │   ├── types/               # TypeScript型定義
  │   │   ├── assets/              # 静的アセット
  │   │   ├── i18n.ts              # 国際化設定
  │   │   ├── App.tsx
  │   │   └── main.tsx
  │   ├── public/locales/          # 多言語リソース (en, ja, ko)
  │   ├── package.json
  │   ├── vite.config.ts
  │   ├── tsconfig.json
  │   └── eslint.config.js
  │
  ├── sql/                         # データベーススクリプト
  ├── docker-compose.yml           # MySQLコンテナ設定
  └── README.md
  ```

  ## 🚀 クイックスタート

  ### 前提条件

  - Java 21（またはそれ以降）
  - Node.js 18+ (npm)
  - Docker & Docker Compose
  - Git

  ### 環境設定

  プロジェクトルートディレクトリに`.env`ファイルを作成して、ローカル環境変数を管理します:

  ```bash
  # .env (バージョン管理から除外 - .gitignoreに追加)
  MYSQL_ROOT_PASSWORD=<ローカル開発用パスワード>
  MYSQL_USER=<ローカル開発用ユーザー>
  MYSQL_PASSWORD=<ローカル開発用パスワード>
  ```

  ### 1️⃣ データベース起動

  ```bash
  # MySQLコンテナ起動
  docker-compose up -d

  # コンテナ状態確認
  docker-compose ps
  ```

  ### 2️⃣ バックエンド実行

  ```bash
  cd backend

  # ビルド
  ./gradlew build

  # 実行 (ローカル環境)
  ./gradlew bootRun
  ```

  ✅ **確認**: `http://localhost:8080`で実行確認

  **API文書**: `http://localhost:8080/swagger-ui.html`

  ### 3️⃣ フロントエンド実行

  ```bash
  cd frontend

  # 依存関係をインストール
  npm install

  # 開発サーバー起動 (ホットリロード)
  npm run dev
  ```

  ✅ **確認**: `http://localhost:5173`で実行確認

  ## 🏗 アーキテクチャと設計原則

  ### バックエンド - Domain-Driven Design (DDD)

  ```
  リクエスト
    ↓
  Controller Layer (REST API)
    ↓
  Service Layer (ビジネスロジック)
    ↓
  Repository Layer (JPA + QueryDSL)
    ↓
  データベース (MySQL)
  ```

  **主な特徴**:

  - **Domain Layer**: コアビジネスロジックのカプセル化
  - **QueryDSL**: タイプセーフな動的クエリ構築
  - **Global Exception Handler**: 一貫したエラーレスポンス形式
  - **Validation**: Spring Validationによる入力データ検証

  ### フロントエンド - コンポーネントベースアーキテクチャ

  - **再利用可能なコンポーネント**: 共通・レイアウトコンポーネント
  - **タイプセーフティ**: TypeScript strictモード対応
  - **APIの一元化**: Axiosベースのクライアント
  - **多言語対応**: i18nextによる国際化
  - **レスポンシブデザイン**: Tailwind CSSユーティリティフレームワーク

  ## 📊 主な機能

  ### APIエンドポイント

  - ✅ RESTful設計原則準拠
  - ✅ Swagger/OpenAPI自動文書化
  - ✅ 体系的な入力検証
  - ✅ 一貫したエラー処理とレスポンス形式（作業必要）

  ### フロントエンド

  - ✅ レスポンシブUI (Tailwind CSS)
  - ✅ 多言語インターフェース (i18next)
  - ✅ TypeScriptベースのタイプセーフティ
  - ✅ 動的ルーティングとSPA実装

  ### 開発者体験 (DX)

  - ✅ ホットモジュールリロード (Vite)
  - ✅ 自動APIクライアントコード生成
  - ✅ コードフォーマッティング自動化 (Prettier)
  - ✅ リント・型チェック自動化

  ## 🧪 テスト戦略

  ### バックエンドテスト実行

  ```bash
  cd backend

  # すべてのテスト実行
  ./gradlew test

  # 特定テストクラス実行
  ./gradlew test --tests "com.kkanyo.gf2tool.domain.doll.service.*"
  ```

  **テストカバレッジ**:

  - `DollControllerTest`: RESTエンドポイント
  - `DollServiceTest`: ビジネスロジック
  - `DollRepositoryTest`: JPAクエリ
  - `DollRepositoryImplTest`: QueryDSLカスタムクエリ
  - `GlobalExceptionHandlerTest`: 例外処理
  - `ValidationExceptionHandlerTest`: 入力検証

  **テストツール**: JUnit 5, H2 (インメモリDB), AssertJ

  ### フロントエンド品質保証

  ```bash
  cd frontend

  # ESLint検査
  npm run lint

  # Prettierフォーマット検査
  npm run format:check

  # 自動フォーマット
  npm run format
  ```

  ## 📚 API文書

  バックエンド実行中、Swagger UIで完全なAPI仕様を確認できます（作業必要）:

  ```
  http://localhost:8080/swagger-ui.html
  ```

  **主なエンドポイント**:
  | メソッド | パス | 説明 |
  |---------|------|------|
  | GET | `/api/dolls` | 一覧取得 |
  | GET | `/api/dolls/{id}` | 詳細取得 |
  | POST | `/api/dolls` | データ作成 |
  | PUT | `/api/dolls/{id}` | データ更新 |
  | DELETE | `/api/dolls/{id}` | データ削除 |

  ## 🌐 多言語対応

  フロントエンドはi18nextベースの国際化を実装:

  | 言語    | コード | 対応状況 |
  | ------- | ------ | -------- |
  | English | en     | ✅ 対応  |
  | 日本語  | ja     | ✅ 対応  |
  | 한국어  | ko     | ✅ 対応  |

  **リソース位置**: `frontend/public/locales/{言語}/`

  ## 💡 主要な学習ポイント

  ### バックエンド

  - **Spring Boot 3.5.11**: 最新版の機能とパフォーマンス改善
  - **Java 21**: モダンなJava文法とパフォーマンス最適化
  - **QueryDSL**: タイプセーフなクエリ構築
  - **テスト駆動開発**: ユニット・統合テスト作成
  - **例外処理**: 一貫したエラーレスポンス設計パターン

  ### フロントエンド

  - **React 19**: 最新Reactの機能とHookパターン
  - **TypeScript**: タイプセーフティが開発生産性向上
  - **Vite**: モダンバンドラーのパフォーマンス利点
  - **Tailwind CSS**: ユーティリティ優先CSS設計
  - **i18n**: 多言語アプリケーション構築

  ### DevOps & 開発環境

  - **Docker & Docker Compose**: 統一された開発/本番環境
  - **プロファイルベース設定**: 環境別設定管理

  ## 🔒 セキュリティと運用上の考慮事項

  ### 開発環境

  - `.env`ファイルでの環境変数管理（バージョン管理から除外）
  - テスト用H2インメモリDBでデータ分離
  - Dockerネットワークによる内部通信

  ## 📝 追加コマンド

  ### APIクライアントコード自動生成

  バックエンド実行中:

  ```bash
  cd frontend
  npm run generate-api
  ```

  OpenAPI仕様をベースにTypeScriptクライアントコードが`src/api/generated/`に生成されます。

  ### コンテナクリーンアップ

  ```bash
  # 実行中のコンテナ停止
  docker-compose down

  # データを含む完全クリーンアップ
  docker-compose down -v
  ```

  ## 📈 今後の改善計画

  - [ ] Dockerイメージの自動ビルド・デプロイ
  - [ ] 自動デプロイパイプライン
  - [ ] AWS環境へのデプロイ

  ## 📝 ライセンス

  このプロジェクトは個人的なポートフォリオプロジェクトです。

  ## 🙋 質問・フィードバック

  改善案や質問があればIssueを登録してください。

  ---

  **言語バージョン**: [한국어](README.md) | [English](README_EN.md)
