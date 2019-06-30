# instagram-sample-app

FacebookAPIを使用したInstagramの投稿記事のサムネイルを取得するアプリです。

FacebookAPIは開発者がクライアントIDを登録することで、SandboxモードのAPIが叩けるようになります。

 詳細: (https://www.instagram.com/developer/)

このアプリでFacebookログインは認証とデータにアクセスする役割を持っています。
ログイン後、特定のユーザーの投稿画像のサムネイルが見られるようになります。
ログアウトするとアクセストークンが無効になる為、サムネイルは表示されなくなります。


# 設計

Activityの債務が広がらないように、ログイン処理、データ取得処理、整形処理の役割にロジックを切り分けています。

関数はモジュール単位で切り分け、Koinを使って依存性を注入しています。 LoginService, 
ImageRepositoryではインターフェースを使用し、クライアントの実装を柔軟に変更できるようになっています。


# View

https://gfycat.com/matureunripeiaerismetalmark.gif

