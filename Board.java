public class Board {
    int[][] board;
    boolean isDecreaseLines;
    boolean isB2B;
    boolean isTSpin;
    boolean isTSpinMini;
    boolean isPC;
    int ren;
    int decreaseLineCount;
    int rubbishLines;
    Board(){
        board=new int[10][44];
        isDecreaseLines=false;
        isTSpin=false;
        isTSpinMini=false;
        isB2B=false;
        isPC=false;
        ren=-1;
        decreaseLineCount=0;
        rubbishLines=0;
    }
    //能不能放块
    public boolean canBePutted(Blocks block,int x,int y,int index){
        int[][] temp=((Block2Array.m.get(block))[index]);
        for(int i=0;i<temp.length;++i){
            for(int j=0;j<temp.length;++j){
                if(!(x+i>=10||y+j>=44||x+i<0||y+j<0)){
                    if(!(board[x+i][y+j]==0||0==temp[j][i])){
                        return false;
                    }
                }
                if(((x+i>=10||x+i<0)||(y+j>=44||y+j<0))&&0!=temp[j][i]){
                    return false;
                }
            }
        }
        return true;
    }
    //放块
    public void addBlock(Blocks block,int x,int y,int index){
        rubbishLines=0;
        int[][] temp=((Block2Array.m.get(block))[index]);
        for(int i=0;i<temp.length;++i){
            for(int j=0;j<temp.length;++j){
                if(!(x+i>=10||y+j>=44||x+i<0||y+j<0)){
                    board[x+i][y+j]+=temp[j][i];
                }
            }
        }
        decreaseLines();
        isPC=isPC();
        calRen();
        if(decreaseLineCount!=0){
            calRubbishLines();
        }
        reset();
        //System.out.println(rubbishLines);
        return;
    }
    //算连击
    private void calRen(){
        if(isDecreaseLines){
            ++ren;
        }
        else{
            ren=-1;
        }
        isDecreaseLines=false;
    }
    //消行并计算消行数
    private void decreaseLines(){
        for(int y=43;y>=0;--y){
            for(int x=0;x<10;++x){
                if(board[x][y]==0){
                    break;
                }
                else if(x==9){
                    decreaseLine(y);
                    isDecreaseLines=true;
                    ++y;
                    ++decreaseLineCount;
                }
            }
        }
    }
    private void decreaseLine(int y){
        for(int i=y;i>0;--i){
            for(int j=0;j<10;++j){
                board[j][i]=board[j][i-1];
            }
        }
        for(int j=0;j<10;j++){
            board[j][0]=0;
        }
    }
    //计算应产生的垃圾行
    private void calRubbishLines(){
        if(isPC){
            rubbishLines+=10;
        }
        if(isTSpin){
            rubbishLines+=(2*decreaseLineCount);
        }
        if(decreaseLineCount==4){
            rubbishLines+=4;
        }
        else if(!isTSpin){
            rubbishLines+=(decreaseLineCount-1);
        }
        //B2B状态下T-Spin和Tetris伤害加1
        if(isB2B&&(isTSpin||isTSpinMini||decreaseLineCount==4)){
            ++rubbishLines;
        }
        //连击垃圾行为0，0，1，1，2，2，3，3，4，4，4，5，5...
        if(ren>0){
            if(ren>10){
                rubbishLines+=5;
            }
            else if(ren==10){
                rubbishLines+=4;
            }
            else{
                rubbishLines+=(ren/2);
            }
        }
    }
    //是不是PC
    private boolean isPC(){
        for(int i=0;i<10;++i){
            for(int j=0;j<44;++j){
                if(board[i][j]!=0){
                    return false;
                }
            }
        }
        return true;
    }
    //重置某些值
    private void reset(){
        if(decreaseLineCount!=0){
            if(decreaseLineCount==4||isTSpin||isTSpinMini){
                isB2B=true;
            }
            else{
                isB2B=false;
            }
        }
        isTSpin=false;
        isTSpinMini=false;
        decreaseLineCount=0;
    }
}