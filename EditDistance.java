import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


public class EditDistance {

    public static void main(String[] args) {
        String inputString1;
        ArrayList<String> input = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> validIndex = new ArrayList<>();
        boolean isValid = true;

        while(scanner.hasNextLine()) {
            inputString1 = scanner.nextLine();
            if(inputString1 == null) {
                break;
            } else {
                input.add(inputString1);
            }
        }

        int validCount = 0;
        for(int i =0; (i < input.size()); i++) {
            if (input.get(i).matches("[a-zA-Z]+") || input.get(i).matches("^$")) {
                validIndex.add(i);
                validCount++;
            } else {
                isValid = false;
            }
        }
        if((validCount == 2) && (isValid)) {
            System.out.println("The number of operations required are " + compute(input.get(validIndex.get(0)), input.get(validIndex.get(1))));
        } else {
            System.out.println("The valid input size is " + validCount);
            System.out.println("Invalid Input");
        }
    }

    private static Integer compute(String inputString1, String inputString2) {
        int i, j;
        int[][] dp = new int[inputString2.length() + 1][inputString1.length() + 1];

        dp[0][0] = 0;
        for (i = 1; i <= inputString2.length(); i++) {
            dp[i][0] = i;
        }

        for (i = 1; i <= inputString1.length(); i++) {
            dp[0][i] = i;
        }

        for (i = 1; i <= inputString2.length(); i++) {
            for (j = 1; j <= inputString1.length(); j++) {
                if(inputString2.charAt(i-1)==inputString1.charAt(j-1)){
                    dp[i][j] = Math.min(dp[i-1][j-1],Math.min( 1+dp[i-1][j], 1+dp[i][j-1]));
                }
                else
                    dp[i][j] = Math.min(1+dp[i-1][j-1], Math.min(1+dp[i-1][j], 1+dp[i][j-1]));


            }
        }

        System.out.println("The dp matrix is ");
        for (i = 0; i <= inputString2.length(); i++) {
            for (j = 0; j <= inputString1.length(); j++) {
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
        Stack<String> operationStack = new Stack<>();
        operationStack.push(inputString2);
        dfs(inputString2.length(), inputString1.length(), inputString1, inputString2, dp, operationStack, 0, inputString2);

        return dp[inputString2.length()][inputString1.length()];
    }
    private static int z=0;
    private static void dfs(int i, int j, String string1, String string2, int[][] dp, Stack<String> operationStack, int cnt, String modifyString) {
        int stringLength1 = string1.length();
        String modifiedString;

        if(i==0 && j==0 && !operationStack.peek().equals(string1)){
            z++;
            System.out.print(z + ") ");
            while (!operationStack.isEmpty()){
                System.out.print(operationStack.pop());
            }
            System.out.println();
            return;
        }

        //Insertion
        if ( i>0 && (dp[i - 1][j] < dp[i][j])) {
            Stack<String> temp = new Stack<>();
            temp.addAll(operationStack);
            modifiedString = modifyString.substring(0, i - 1) + modifyString.substring(i);
            temp.push(modifiedString + " insert " + modifyString.charAt(i-1) + " -> ");
            dfs(i - 1, j, string1, string2, dp, temp, cnt + 1, modifiedString);
        }

        //Replace
        if ( i>0 && j>0 && (string1.charAt(j-1)==string2.charAt(i-1) || 1 + dp[i - 1][j - 1] == dp[i][j])) {
            //Characters are Equal.
            if (string1.charAt(j-1)==string2.charAt(i-1)) {
                dfs(i - 1, j - 1, string1, string2, dp, operationStack, cnt, modifyString);
            } else {
                Stack<String> temp = new Stack<>();
                temp.addAll(operationStack);
                modifiedString = modifyString.substring(0, i - 1) + string1.charAt(j - 1) + modifyString.substring(i, modifyString.length());
                temp.push(modifiedString + " replace " + string1.charAt(j - 1) + " by " + string2.charAt(i - 1) + " -> ");
                dfs(i - 1, j - 1, string1, string2, dp, temp, cnt + 1, modifiedString);
            }
        }

        //Delete
        if ((j>0 && (dp[i][j - 1] + 1 == dp[i][j]))) {
            //Characters are equal

            Stack<String> temp = new Stack<>();
            temp.addAll(operationStack);
            int k = stringLength1 - j;
            modifiedString = modifyString.substring(0, modifyString.length()-k)+string1.charAt(j - 1) + modifyString.substring(modifyString.length()-k, modifyString.length());
            temp.push(modifiedString + " delete " + string1.charAt(j - 1) + " -> ");
            dfs(i, j - 1, string1, string2, dp, temp, cnt + 1, modifiedString);
        }
    }
}
