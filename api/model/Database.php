<?php
class Database
{
    private $pdo;

    public function __construct($pdo)
    {
        $this->pdo = $pdo;
    }

    public function getFlashcards()
    {
        $stmt = $this->pdo->query("SELECT * FROM flashcards");
        return $stmt->fetchAll();
    }

    public function createFlashcard($data)
    {
        $stmt = $this->pdo->prepare("INSERT INTO flashcards (id, name, image, description) VALUES (?, ?, ?, ?)");
        return $stmt->execute([$data['id'], $data['name'], $data['image'], $data['description']]);
    }
}
?>