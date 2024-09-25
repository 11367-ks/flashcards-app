<?php
header('Content-Type: application/JSON');
include '../inc/config.php';
include '../model/Database.php';
include '../model/FlashcardsModel.php';

$db = new Database($pdo);

$method = $_SERVER['REQUEST_METHOD'];
$request = explode("/", $_SERVER["REQUEST_URI"]);

switch ($method) {
    case 'GET':
        echo "used GET";
        break;
    case 'POST':
        echo "used POST";
        break;
    case 'PUT':
        echo "used PUT";
        break;
    case 'DELETE':
        echo "used DELETE";
        break;
    default:
        header("HTTP/1.0 405 Method Not Allowed");
        break;
}
?>