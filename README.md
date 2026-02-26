# Git運用ルール

本プロジェクトでは、以下の3つの主要ブランチで運用します。

## 1. ブランチの役割と用途

| ブランチ名 | 役割 | マージ元 / 運用方針 |
| :--- | :--- | :--- |
| **`main`** | 最終ブランチ。 | 直接コミット禁止。`develop`からのマージのみ。 |
| **`develop`** | 開発の統合を行うブランチ。 | `feature`ブランチからのマージを受け入れる。 |
| **`feature/`** | 機能開発・バグ修正など、個別の作業を行うブランチ。 | `develop`から切られ、作業完了後に`develop`に戻る。 |

***

## 2. 開発ワークフロー（Featureブランチの運用）

### 1. Issue作成
新しい作業は、必ずIssueを作成し、Issueに紐づいたフィーチャーブランチを切って進めてください。

テンプレートを使って詳細な説明を記載。（任意）
```テンプレ
## 概要
## 目的 / 背景
## 作業内容
- [ ] 
- [ ] 

## 備考
```

### 2. 作業ブランチの作成
作業を開始する際は、必ず最新の`develop`ブランチからブランチを作成し、移動してください。

ブランチの命名規則は、feature/[issue番号]-任意

```Git bash
# 1. developブランチに移動し、最新の状態にする
git checkout develop
git pull origin develop

# 2. Issue番号を含めたブランチ名で作成・切り替え
git checkout -b feature/123-user-registration
```

### 3. 開発・修正~commitまで
featureブランチでの作業が完了後、commitを行ってください。

コミットメッセージは、「カテゴリ：[issue番号]　備考」の形式としてください。

カテゴリは以下を使用していますが、適宜わかりやすいものをしても問題ないこととする。

| カテゴリ | 意味 |
| :--- | :--- |
| **`feat`** | 新機能追加 |
| **`fix`** | バグ修正 |
| **`refactor`** | コードの整理（挙動の変更なし） |
| **`wip`** | 作業途中（一時保存） |

```Git bash
# 1. 変更内容を確認
git status

# 2. 修正分をステージングしてコミット
git add .  //全て追加　or git add -u //修正分のみ
git commit -m "feat: #123 ユーザー登録機能の実装"
```

### 4. pushとプルリクエスト（PR）まで
GitHubへpushし、ブラウザからPRを作成してください。

```Git bash
# 1. push
git push origin feature/123-user-registration
```

push後、GitHubのリポジトリ画面をブラウザで開いてください。

ページ上部に 「Compare & pull request」 という黄色いバーが出ているはずなのでクリックしてください。

タイトル、説明を記載し「Create pull request」ボタンを押してください。
※説明欄にCloses issue番号を記載してください。
例：Closes #123

### 5. セルフチェックとマージ

### 6.ローカル更新作業

```Git bash
# developに戻る
git checkout develop

# GitHubでマージされた最新の状態を取り込む
git pull origin develop

# 使い終わった作業ブランチを消す（任意ですが、整理のために推奨）
git branch -d feature/作成したブランチ名
```
