package model.bean;

import javafx.collections.ObservableList;

public interface DashControllerRequires {
    
    //possibilidades de personagem
    public String[] getPossibleCharacterSuggestions();
    
    //Possibilidades de super poderes
    public String[] getPossibleSuperPowerSuggestions();
    
    //Adicionar um novo super poder para ser calculado
    public void addNewCharacteristc(String characteristic);
    
    //Calcular
    public void calculate();
    
    //Pegar os nomes dos algorítimos
    public ObservableList<String> getAlgorithmsPossibilities();
    
    //Pegar o Observable list com os resultados do calculo
    public ObservableList<?> getObservableListResults();
    
    //Pegar personagem
    public Object getCharacter(String name);
    
}
