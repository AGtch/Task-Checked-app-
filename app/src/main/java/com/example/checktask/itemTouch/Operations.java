package com.example.checktask.itemTouch;
// this interface to show dialog to user
public interface Operations {

    /**
     * @param position is the position of item in is the position of item in recyclerView
     * @param title is the title of Alert dialog
     * @param message to ask user
     * method to ask if he want to delete task use it when user swap to delete task or check it
     */
    void delete(int position , String title , String message);
}
