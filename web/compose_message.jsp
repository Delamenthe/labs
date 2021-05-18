<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form action="/lab8/send_message.do" method="post">
    Text message:
    <input type="text" name="message" style="width: 50%">
    <input type="submit" value="Send message">
    <a href="/lab8/logout.do" target="_top">quit chat</a>

    <div>
        <input type="radio" id="Choice1" name="choice" value="normal" checked = "checked">
        <label for="Choice1">Simple</label>

        <input type="radio" id="Choice2" value="whisper" name="choice">
        <label for="Choice2">Whisper</label>

        <input type="radio" id="Choice3" value="scream" name="choice">
        <label for="Choice3">Scream</label>
    </div>

    <br>
</form>


</body>
</html>
