<?php
header('Content-Type: application/JSON');
include '../inc/config.php';

$method = $_SERVER['REQUEST_METHOD'];
$request = explode("/", $_SERVER["REQUEST_URI"]);
$input = json_decode(file_get_contents('php://input'), true);

switch ($method) {
    case 'GET':
        getFlashcards($pdo);
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

function getFlashcards($pdo)
{
    $stmt = $pdo->prepare("SELECT * FROM flashcards");
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($result);
}
