package com.squorpikkor.app.trenkaassistant4;

class Exercise {

    private int ID;
    private String name;
    //the_best_training
    //today_training    //Сохранение текущей тренировки упражнения, чтобы при переключении
                        // на другое упражнение, не терялись данные на текущее (например,
                        // если оно ещё не завершено)


    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }


}
