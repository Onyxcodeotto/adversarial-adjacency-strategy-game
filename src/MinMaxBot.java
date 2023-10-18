import javax.naming.TimeLimitExceededException;

public class MinMaxBot extends Bot{
    private int maxDepth = 7;
    private int MAX_INIT = -100;
    private int MIN_INIT = 100;
    private long time;

    public MinMaxBot(GameState state) {
        super(state);
    }

    public int[] move() {
    // try{
        time = System.currentTimeMillis();
        MinMaxIntermediate result = this.solve(true, this.MAX_INIT, this.MIN_INIT, maxDepth);
        System.out.print(result.getMove().getX());
        System.out.print(" ");
        System.out.println(result.getMove().getY());
        return new int[]{(int) (result.getMove().getX()), (int) (result.getMove().getY())};
    //}
    /*catch(TimeLimitExceededException e){
        System.out.println("Random Move");
        return int[]{0, 0};
    }*/
    }

    public MinMaxIntermediate solve(boolean isMaximizing, int alpha, int beta, int depth){//return x, y, and score
        /*if(time-System.currentTimeMillis()>5000F){
            throw new TimeLimitExceededException();
        }*/
        if(this.state.isGameEnded() || depth==0){
        //    this.getState().printReport();
            return new MinMaxIntermediate(this.state.getLastMove(), this.state.evaluate());
        }else{

            if(isMaximizing){

                MinMaxIntermediate bestMove = new MinMaxIntermediate(new Coordinate(-1,-1), this.MAX_INIT);
                int maxval = this.MAX_INIT;
                int localAlpha = alpha;
                for (Coordinate move : this.state.getEmptyTile()) {
                    this.state.fill(move, this.state.getAIPiece());
                    MinMaxIntermediate result = this.solve(false, localAlpha, beta, depth-1);
                    this.state.reverse();
                    int newval = result.getVal();
                    if(newval>maxval){
                        bestMove = result;
                        maxval = newval;
                    }

                    if(newval>localAlpha){
                        localAlpha = newval;
                    }
                    if(localAlpha>=beta){
                        //return dummy or result, whichever more efficient
                        return result;
                    }
//                    if(maxval>0){
//                        return bestMove;
//                    }
                }
                if(depth == maxDepth){
                    System.out.print("Best Score"); System.out.println(bestMove.getVal());
                }
                return bestMove;
            }else{
                //minimizing
                MinMaxIntermediate bestMove = new MinMaxIntermediate(new Coordinate(-1,-1), this.MIN_INIT);
                int minvalue = this.MIN_INIT;
                int localBeta = beta;
                for (Coordinate move : this.state.getEmptyTile()){
                    this.state.fill(move, this.state.getPlayerPiece());
                    MinMaxIntermediate result = this.solve(true, alpha, localBeta, depth-1);
                    this.state.reverse();
                    int newValue = result.getVal();
                    if(newValue<minvalue){
                        bestMove = result;
                        minvalue = newValue;
                    }
                    if(newValue< localBeta){
                        localBeta = newValue;

                    }
                    if(alpha>=localBeta){
                        return result;
                    }
//                    if(minvalue<0){
//                        return bestMove;
//                    }

                }
                return bestMove;
            }


        }
    }
}


//what happen if i store best move outside solve?
//if times out,



// def minMax(self, isMaximizing, alpha, beta):
// # Draw end
// if self.board.checkDraw() and self.board.checkWinner() == "-":
//     return 0, None
// # Winning end
// elif(self.board.checkWinner() == self.aiPiece):
//     return 100, None
// elif(self.board.checkWinner() == self.playerPiece):
//     return -100, None
// else:
//     if isMaximizing:
//         maxval = -999
        
//         for path in self.board.getEmptyTile():
//             self.board.fill(path[0],path[1], self.aiPiece)
//             val, _ = self.minMax(False, alpha, beta)
//             self.board.remove(path[0], path[1])
//             if val>maxval:
//                 maxval = val
//                 move = path
//             alpha = max(alpha, val)
//             if alpha>=beta:
//                 return maxval, move
//         return maxval, move
//     else:
//         minval = 999
//         for path in self.board.getEmptyTile():
//             self.board.fill(path[0],path[1], self.playerPiece)
//             val, _ = self.minMax(True,alpha,beta)
//             self.board.remove(path[0], path[1])
//             if val < minval:
//                 minval = val
//                 move = path
//             beta = min(beta, minval)
//             if alpha>=beta:
//                 return minval, move
//         return minval, move   