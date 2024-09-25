<?php
class FlashcardsModel
{
    private $db;

    public function __construct($db)
    {
        $this->db = $db;
    }

    public function getAllFlashcards($db)
    {
        return $this->db->query("SELECT * FROM flashcards")->fetchAll();
    }

    public function getFlashcard($id)
    {
        return $this->db->query("SELECT * FROM flashcards WHERE id = ?", [$id])->fetch();
    }

    public function createFlashcard($name, $image, $description)
    {
        return $this->db->query("INSERT INTO flashcards (name, image, description) VALUES (?, ?)", [$name, $image, $description]);
    }

    public function updateFlashcard($id, $name, $image, $description)
    {
        return $this->db->query("UPDATE flashcards SET name = ?, image = ?, description = ? WHERE id = ?", [$name, $image, $description, $id]);
    }

    public function deleteFlashcard($id)
    {
        return $this->db->query("DELETE FROM flashcards WHERE id = ?", [$id]);
    }
}
?>