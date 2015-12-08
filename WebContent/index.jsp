<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    

    <title>OTS</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

  
  </head>

  <body>
  <div class="container">
      <div class="header">
        <nav>
          <ul class="nav nav-pills pull-right">
<!--             <li role="presentation" class="active"><a href="#">Home</a></li> -->
<!--             <li role="presentation"><a href="#">About</a></li> -->
          </ul>
        </nav>
        <h3 class="text-muted">Oil Transaction System</h3>
      </div>
	 </div>
    <div class="container">

      <form class="form-signin" action="login" role="form">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">User ID</label>
        <input type="text" name="user" id="inputUserid" class="form-control" placeholder="User ID" required autofocus>
        
        <label for="inputPassword"   class="sr-only">Password</label>
        <input type="password" name="pwd" id="inputPassword" class="form-control" placeholder="Password" required>
        <br/>Select your Role:
        <input type="radio" name="roleradio" value="0" required/>Client
        <input type="radio" name="roleradio" value="1" required/>Trader
        <input type="radio" name="roleradio" value="2" required/>Manager
        <br/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>

    </div> <!-- /container -->


    
  </body>
</html>
