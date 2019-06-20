<?php 
 
 if($_SERVER['REQUEST_METHOD']=='GET'){
 //Getting values 
 $user_email = $_GET['user_email'];
 
 //Creating sql query
 $sql = "SELECT user_email FROM wp_users WHERE user_email='$user_email'";
 
 //importing dbConnect.php script 
 require_once('init.php');
 
 //executing query
 $result = mysqli_query($con,$sql);
 
// //fetching result
$check = mysqli_fetch_array($result);
     
     $final = array();
     array_push($final,array(
			"name"=>$check['user_email']
			)
		);
     
 
 //if we got some result 
 if(isset($check)){
 //displaying success 
// echo "success";
 //displaying name
echo json_encode(array("result"=>$final));
     
 }else{
 //displaying failure
 echo "failure";
 }
 mysqli_close($con);
 }