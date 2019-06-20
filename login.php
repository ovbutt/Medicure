<?php 
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 //Getting values 
 $user_email = $_POST['user_email'];
 $user_pass = $_POST['user_pass'];
 
 //Creating sql query
 $sql = "SELECT * FROM signup WHERE user_email='$user_email' AND user_pass='$user_pass'";
 
 //importing dbConnect.php script 
 require_once('init.php');
 
 //executing query
 $result = mysqli_query($con,$sql);
 
 //fetching result
 $check = mysqli_fetch_array($result);
 
 //if we got some result 
 if(isset($check)){
 //displaying success 
 echo "success";
 }else{
 //displaying failure
 echo "failure";
 }
 mysqli_close($con);
 }