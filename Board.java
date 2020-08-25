public class Board {
    int[][] board;
    Board(){
        board=new int[10][44];
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
        int[][] temp=((Block2Array.m.get(block))[index]);
        for(int i=0;i<temp.length;++i){
            for(int j=0;j<temp.length;++j){
                if(!(x+i>=10||y+j>=44||x+i<0||y+j<0)){
                    board[x+i][y+j]+=temp[j][i];
                }
            }
        }
        decreaseLines();
        return;
    }
    //消行
    private void decreaseLines(){
        for(int y=43;y>=0;--y){
            for(int x=0;x<10;++x){
                if(board[x][y]==0){
                    break;
                }
                else if(x==9){
                    decreaseLine(y);
                    ++y;
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
    //增加垃圾行
    public void rubbishLines(int count){}
}