package it.univaq.disim.oop.lchess;


import it.univaq.disim.oop.lchess.business.BusinessException;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import it.univaq.disim.oop.lchess.controller.Controller;

public class LChessApplication {


    public static void main(String[] args) {
    	new Controller().start();
    }


    


}
