<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>重置密码</title>
</head>
<body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
    <div style="max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #eee; border-radius: 8px;">
        <h2>您好，${username}！</h2>
        <p>您请求重置账户密码，请点击下方按钮完成操作：</p>
        <div style="text-align: center; margin: 20px 0;">
            <a href="${resetUrl}" 
               style="display: inline-block; padding: 10px 20px; background-color: #1890ff; color: white; text-decoration: none; border-radius: 4px;">
                立即重置密码
            </a>
        </div>
        <p>如果按钮无法点击，请复制以下链接到浏览器中打开：</p>
        <p><a href="${resetUrl}" style="color: #1890ff;">${resetUrl}</a></p>
        <p style="color: #888; font-size: 12px;">
            此链接在 1 小时内有效。如非本人操作，请忽略此邮件。
        </p>
    </div>
</body>
</html>