<?php 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 //Getting values 
 $user_email= $_POST["user_email"];
 $user_pass= $_POST["user_pass"];
 
 
  
 require_once('init.php');
  
 
 $sql = "UPDATE
  signup
  SET 
  user_pass= '$user_pass'
  WHERE user_email= '$user_email';";


 //Updating database table 
 if(mysqli_query($con,$sql)){
 
  echo 'Password Reset Successfully';
}
else

{
 echo 'Something Went Wrong! Could Not Reset Password Try Again';
 }
  
 
 //closing connection 
 mysqli_close($con);
 }