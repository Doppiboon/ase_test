package com.ase.test.logic;

import com.ase.test.model.Request;
import com.ase.test.model.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Verifier {
    public Request buildAndVerifyRequest(Request rootRequest) {
        rootRequest.setCorrect(true);

        for(Solution s : rootRequest.getSolutions()) {
            //checkSolution(rootRequest, s);
        }

        computeAllSolutions(rootRequest);

        return null;
    }

    private void computeAllSolutions(Request request) {
        String orgReqEquation = reshapeEquation(request.getEquation());

        String leftPartOfEquation = orgReqEquation.split("=")[0];

        for(int i = 0; i < leftPartOfEquation.length(); i=i+2) {
            char curValI = leftPartOfEquation.charAt(i);

            if (isWithinMatchAllowed(curValI)) {
                List<Character> possibleChangesWithin = getNumbersWithinChanges(curValI);

                for (char p : possibleChangesWithin) {
                }
            }

            for(int j = 0; j < leftPartOfEquation.length(); j=j+2) {
                if (i == j)
                    continue;

                char curValJ = leftPartOfEquation.charAt(j);

                if (isRemoveMatchAllowed(curValI) && isAddMatchAllowed(curValJ)) {
                    List<Character> posChangesI = getNumbersRemoveChanges(curValI);
                    List<Character> posChangesJ = getNumbersAddChanges(curValJ);

                    for (char eI : posChangesI) {
                        for (char eJ : posChangesJ) {
                            String newEquation = setNewValues(leftPartOfEquation, i, j, eI, eJ);
                            int sum = calculateLeftSum(newEquation);

                            if (sum % 10 == 0) {
                                if (request.getSolutions()
                                        .stream()
                                        .filter(n -> n.getEquation().equals(newEquation)).toArray().length == 0) {
                                    Solution newComputedSolution = new Solution(false, newEquation, j + 1);
                                    request.addSolution(newComputedSolution);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private String setNewValues(String equation, int posI, int posJ, char valI, char valJ) {
        char[] result = equation.toCharArray();

        result[posI] = valI;
        result[posJ] = valJ;

        return result.toString();
    }

    private List<Character> getNumbersWithinChanges(char num) {
        List<Character> result = new ArrayList<>();

        switch (num) {
            case '0':
                result.add('9');
                result.add('6');
                break;
            case '2':
                result.add('3');
                break;
            case '3':
                result.add('5');
                result.add('2');
                break;
            case '5':
                result.add('3');
                break;
            case '6':
                result.add('0');
                result.add('9');
                break;
            case '9':
                result.add('6');
                result.add('0');
                break;
        }

        return result;
    }

    private List<Character> getNumbersRemoveChanges(char num) {
        List<Character> result = new ArrayList<>();

        switch (num) {
            case '6':
                result.add('5');
                break;
            case '7':
                result.add('1');
                break;
            case '8':
                result.add('0');
                result.add('6');
                result.add('9');
                break;
            case '9':
                result.add('3');
                break;
        }

        return result;
    }

    private List<Character> getNumbersAddChanges(char num) {
        List<Character> result = new ArrayList<>();

        switch (num) {
            case '0':
                result.add('8');
                break;
            case '1':
                result.add('7');
                break;
            case '3':
                result.add('9');
                break;
            case '5':
                result.add('6');
                result.add('9');
                break;
            case '6':
                result.add('8');
                break;
            case '9':
                result.add('8');
                break;
        }

        return result;
    }

    private void checkSolution(Request request, Solution solution) {
        String orgReqEquation = reshapeEquation(request.getEquation());
        String orgSolEquation = reshapeEquation(solution.getEquation());

        int sumSolution = calculateLeftSum(orgSolEquation);
        int sumRequest = calculateLeftSum(orgReqEquation);

        if (sumRequest % 10 != sumSolution % 10)
            request.setCorrect(false);
    }

    private int calculateLeftSum(String equation) {
        int result = 0;

        int LEFT = 0;
        String strFormula = equation.split("=")[LEFT];

        for (int i = 0; i < strFormula.length(); ) {
            if (i==0) {
                char c = strFormula.charAt(i);
                result += Integer.parseInt(String.valueOf(c));
                i++;
            }
            else if (strFormula.charAt(i) == '+') {
                char c = strFormula.charAt(i+1);
                result += Integer.parseInt(String.valueOf(c));
                i=i+2;
            }
            else if (strFormula.charAt(i) == '-') {
                char c = strFormula.charAt(i+1);
                result -= Integer.parseInt(String.valueOf(c));
                i=i+2;
            }
        }

        return result;
    }

    private String reshapeEquation(String equation) {
        String result = "";

        String[] splitEquation = equation.split("=");

        int LEFT = 0;
        int RIGHT = 1;

        StringBuilder leftEq = new StringBuilder(splitEquation[LEFT]);
        String rightEq = splitEquation[RIGHT];

        for(int i = 0; i < rightEq.length(); i++) {
            if (i == 0) {
                leftEq.append("-").append(rightEq.charAt(i));
            }
            else if (rightEq.charAt(i) == '+') {
                leftEq.append("-").append(rightEq.charAt(i+1));
                i++;
            }
            else if (rightEq.charAt(i) == '-') {
                leftEq.append("+").append(rightEq.charAt(i+1));
                i++;
            }
        }

        rightEq = "0";
        result = leftEq + "=" + rightEq;

        return result;
    }

    private boolean isAddMatchAllowed(char num) {
        return num != '2'
                && num != '4'
                && num != '7'
                && num != '8';
    }

    private boolean isRemoveMatchAllowed(char num) {
        return num == '6'
                || num == '7'
                || num == '8'
                || num == '9';
    }

    private boolean isWithinMatchAllowed(char num) {
        return num != '1'
                && num != '4'
                && num != '7'
                && num != '8';
    }
}
