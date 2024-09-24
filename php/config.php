<?php
  $host = 'db';
  $db_name = 'rest_api_flashcards';
  $username = 'root';
  $password = 'example'; # storing password in variable just for testing purposes

  try {
    $pdo = new PDO("mysql:host=$host;dbname=$db_name", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
  }
  catch (PDOException $e) {
    echo "Connection failed: " . $e->getMessage();
  }
?>