function getRootWin(){   
    var win = window;   
    while (win != win.parent){   
        win = win.parent;   
    }   
    return win;   
}  
