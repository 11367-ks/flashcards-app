<?php
try {
    $pdo = new PDO('mysql:host=db;dbname=rest_api_flashcards', 'root', 'example');
    $pdo->setAttribute(PDO::MYSQL_ATTR_USE_BUFFERED_QUERY, false);
    $pdo->query("INSERT INTO flashcards VALUES (0, 'testing', 'some', 'data')");
} catch (PDOException $e) {
    echo 'Connection failed: ' . $e->getMessage();
}
?>