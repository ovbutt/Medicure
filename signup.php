<?php
 require "init.php";
 $user_nicename = $_POST["user_nicename"];
 $user_email = $_POST["user_email"];
 $user_pass = $_POST["user_pass"];
 $user_mbl = $_POST["user_mbl"];


 $sql_query = "INSERT INTO signup (user_nicename,user_email,user_mbl,user_pass) VALUES('$user_nicename','$user_email','$user_mbl','$user_pass')";
 if(mysqli_query($con,$sql_query))
 {

echo "Successfully Register";
 }
 else
 {
 echo "Failed.......Try Again..";
 }
 ?>