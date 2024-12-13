import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Monty_Hall extends JFrame {
    private final JButton[] doors = new JButton[3];
    private final ImageIcon goat = new ImageIcon("images/goat.png");
    private final ImageIcon car = new ImageIcon("images/car.png");
    private final ImageIcon door = new ImageIcon("images/door.png");
   

    private JButton door_1, door_2, door_3;
    private JButton switchButton, stayButton, newturnButton, resetButton;
    private JLabel winLabel;
    private JLabel title;
    private boolean isSwitched = false;
    private JLabel totalGamesLabel;
    private JLabel changeChoiceLabel;
    private JLabel stayChoiceLabel;

    private int selectedDoor = -1;
    private int winningDoor;
    private int openedDoor = -1;
    private int[] remainingDoors = new int[2];
    private boolean doorClicked = false;

    private int winsSwitch = 0;
    private int lossesSwitch = 0;
    private int winsStay = 0;
    private int lossesStay = 0;
    private int totalGames = 0;

    public Monty_Hall() {
        this.setTitle("Monty Hall Simulator");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());   
        
        // Create door buttons
        door_1 = createDoorButton(0);
        door_2 = createDoorButton(1);
        door_3 = createDoorButton(2);


        JPanel doorsPanel = new JPanel(new GridLayout(1, 3, 70, 70));
        doorsPanel.setPreferredSize(new Dimension(800, 400));
        doorsPanel.add(door_1);
        doorsPanel.add(door_2);
        doorsPanel.add(door_3);
        doorsPanel.setBackground(Color.GRAY);

        // Buttons for switching, staying, and resetting
        JPanel buttonPanel = new JPanel();
        switchButton = new JButton("Switch");
        stayButton = new JButton("Stay");
        newturnButton = new JButton("New Turn");
        resetButton = new JButton("Reset");
        //rulesButton = new JButton("Rules");

        switchButton.setEnabled(false);
        stayButton.setEnabled(false);

        buttonPanel.add(switchButton);
        buttonPanel.add(stayButton);
        buttonPanel.add(newturnButton);
        buttonPanel.add(resetButton);
        buttonPanel.setBackground(Color.GRAY);
        
        // Set color for buttons
        switchButton.setBackground(new Color(150, 110, 0));  // Set background color to red
        switchButton.setForeground(Color.WHITE);  // Set text color to white

        stayButton.setBackground(new Color(150, 110, 0));  // Set background color to green
        stayButton.setForeground(Color.WHITE);  // Set text color to white

        newturnButton.setBackground(new Color(150, 110, 0)); // Set background color to blue
        newturnButton.setForeground(Color.WHITE);  // Set text color to white

        resetButton.setBackground(new Color(150, 110, 0));  // Set background color to yellow
        resetButton.setForeground(Color.WHITE);  // Set text color to black

        
        title = new JLabel("Monty Hall Simulator", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);


        //Add a border with background color
        winLabel = new JLabel();
        winLabel.setText("<html><body>"
        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total games: " + totalGames + "<br>"
        + "Change choice&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Wins: " + winsSwitch + " | Losses: " + lossesSwitch + "<br>"
        + "Stay choice&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Wins: " + winsStay + " | Losses: " + lossesStay
        + "</body></html>");
        // Not bold
        winLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        winLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
        winLabel.setOpaque(true); 
        winLabel.setBackground(Color.GRAY);    
        winLabel.setForeground(Color.WHITE);
        //winLabel.setForeground(Color.WHITE);
          




        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 400, 40));
        centerPanel.add(title, BorderLayout.NORTH);
        centerPanel.add(doorsPanel);
        centerPanel.add(buttonPanel);
        centerPanel.setBackground(Color.GRAY);
        centerPanel.add(winLabel, BorderLayout.SOUTH);

        this.add(centerPanel, BorderLayout.CENTER);

        // Set the winning door and the remaining doors
        winningDoor = getWinningDoor();
        remainingDoors = getRemainingDoors(winningDoor);
        
        // if door 1 is clicked, set selectedDoor to 1 and open a door which is not winning door and not selected door
        door_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doorClicked) return; // Ignore the click if already clicked
                doorClicked = true;  // Mark that a door has been clicked
                selectedDoor = 1;     
                System.out.println("Open door previous: " + openedDoor);           
                System.out.println("Selected door: " + selectedDoor);

                if (remainingDoors[0] == selectedDoor) {
                    openedDoor = remainingDoors[1];
                } else if (remainingDoors[1] == selectedDoor) {
                    openedDoor = remainingDoors[0];
                } else {
                    // if the selected door is not in the remaining doors, open a random door between the remaining doors
                    Random rand = new Random();
                    int randomIndex = rand.nextInt(2);
                    openedDoor = remainingDoors[randomIndex];
                }
                
                System.out.println("Opened door: " + openedDoor);
                switchButton.setEnabled(true);
                stayButton.setEnabled(true);
                // change the icon of the opened door to goat
                if (openedDoor == 2) {
                    door_2.setIcon(resizeIcon(goat, door_2.getWidth(), door_2.getHeight() + 28));
                } else if (openedDoor == 3) {
                    door_3.setIcon(resizeIcon(goat, door_3.getWidth(), door_3.getHeight() + 28 ));
                }

            }
        });

        // if door 2 is clicked, set selectedDoor to 2 and open a door which is not winning door and not selected door
        door_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doorClicked) return; // Ignore the click if already clicked
                doorClicked = true;  // Mark that a door has been clicked
                selectedDoor = 2;
                System.out.println("Opened door previous: " + openedDoor);
                System.out.println("Selected door: " + selectedDoor);

                if (remainingDoors[0] == selectedDoor) {
                    openedDoor = remainingDoors[1];
                } else if (remainingDoors[1] == selectedDoor) {
                    openedDoor = remainingDoors[0];
                } else {
                    // if the selected door is not in the remaining doors, open a random door between the remaining doors
                    Random rand = new Random();
                    int randomIndex = rand.nextInt(2);
                    openedDoor = remainingDoors[randomIndex];
                }
                System.out.println("Opened door later: " + openedDoor);
                switchButton.setEnabled(true);
                stayButton.setEnabled(true);
                // change the icon of the opened door to goat
                if (openedDoor == 1) {
                    door_1.setIcon(resizeIcon(goat, door_1.getWidth(), door_1.getHeight() + 28));
                } else if (openedDoor == 3) {
                    door_3.setIcon(resizeIcon(goat, door_3.getWidth(), door_3.getHeight() + 28));
                } 
            }
        });

        // if door 3 is clicked, set selectedDoor to 3 and open a door which is not winning door and not selected door
        door_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doorClicked) return; // Ignore the click if already clicked
                doorClicked = true;  // Mark that a door has been clicked
                selectedDoor = 3;
                System.out.println("Selected door: " + selectedDoor);              
                if (remainingDoors[0] == selectedDoor) {
                    openedDoor = remainingDoors[1];
                } else if (remainingDoors[1] == selectedDoor) {
                    openedDoor = remainingDoors[0];
                } else if (remainingDoors[0] != selectedDoor && remainingDoors[1] != selectedDoor) {
                    // if the selected door is not in the remaining doors, open a random door between the remaining doors
                    Random rand = new Random();
                    int randomIndex = rand.nextInt(2);
                    openedDoor = remainingDoors[randomIndex];
                }
                System.out.println("Opened door: " + openedDoor);
                switchButton.setEnabled(true);
                stayButton.setEnabled(true);
                // change the icon of the opened door to goat
                if (openedDoor == 1) {
                    door_1.setIcon(resizeIcon(goat, door_1.getWidth(), door_1.getHeight() + 28));
                } else if (openedDoor == 2) {
                    door_2.setIcon(resizeIcon(goat, door_2.getWidth(), door_2.getHeight() + 28));
                }
            }
        });

        // if switch button is clicked, change the selected door to the other door
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if the open door is 1 and selected door is 2, switch to 3
                if (openedDoor == 1 && selectedDoor == 2) {
                    selectedDoor = 3;
                }
                // if the open door is 1 and selected door is 3, switch to 2
                else if (openedDoor == 1 && selectedDoor == 3) {
                    selectedDoor = 2;
                }
                // if the open door is 2 and selected door is 1, switch to 3
                else if (openedDoor == 2 && selectedDoor == 1) {
                    selectedDoor = 3;
                }
                // if the open door is 2 and selected door is 3, switch to 1
                else if (openedDoor == 2 && selectedDoor == 3) {
                    selectedDoor = 1;
                }
                // if the open door is 3 and selected door is 1, switch to 2
                else if (openedDoor == 3 && selectedDoor == 1) {
                    selectedDoor = 2;
                }
                // if the open door is 3 and selected door is 2, switch to 1
                else if (openedDoor == 3 && selectedDoor == 2) {
                    selectedDoor = 1;
                }
                System.out.println("Switched to door: " + selectedDoor);
                isSwitched = true;
                checkWin();
                showAllDoors();
                // disable the switch and stay buttons, set the selected door to -1, opened door to -1 and winning door to a random number
                switchButton.setEnabled(false);
                stayButton.setEnabled(false);
            }
        });

        // if stay button is clicked, keep the selected door
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Stayed with door: " + selectedDoor);
                isSwitched = false;
                checkWin();
                showAllDoors();
                // disable the switch and stay buttons, set the selected door to -1, opened door to -1 and winning door to a random number
                switchButton.setEnabled(false);
                stayButton.setEnabled(false);
            }
        });
        // if new turn button is clicked, reset the game
        newturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Previous:" + openedDoor);
                System.out.println("New Turn");
                openedDoor = -1;
                selectedDoor = -1;
                doorClicked = false;
                winningDoor = getWinningDoor();
                remainingDoors = getRemainingDoors(winningDoor);
                door_1.setIcon(resizeIcon(door, door_1.getWidth(), door_1.getHeight() + 28));
                door_2.setIcon(resizeIcon(door, door_2.getWidth(), door_2.getHeight() + 28));
                door_3.setIcon(resizeIcon(door, door_3.getWidth(), door_3.getHeight() + 28));
                switchButton.setEnabled(false);
                stayButton.setEnabled(false);
                          
                
            }
        });   
        // if reset button is clicked, reset the game and the statistics
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reset");
                winsSwitch = 0;
                lossesSwitch = 0;
                winsStay = 0;
                lossesStay = 0;
                totalGames = 0;
                winLabel.setText("<html><body>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total games: " + totalGames + "<br>"
                + "Change choice&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Wins: " + winsSwitch + " | Losses: " + lossesSwitch + "<br>"
                + "Stay choice&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Wins: " + winsStay + " | Losses: " + lossesStay
                + "</body></html>");
                openedDoor = -1;
                selectedDoor = -1;
                doorClicked = false;
                winningDoor = getWinningDoor();
                remainingDoors = getRemainingDoors(winningDoor);
                door_1.setIcon(resizeIcon(door, door_1.getWidth(), door_1.getHeight() + 28));
                door_2.setIcon(resizeIcon(door, door_2.getWidth(), door_2.getHeight() + 28));
                door_3.setIcon(resizeIcon(door, door_3.getWidth(), door_3.getHeight() + 28));
                switchButton.setEnabled(false);
                stayButton.setEnabled(false);              
            }
        });

    }
    private void showAllDoors() {
        if (winningDoor == 1) {
            door_1.setIcon(resizeIcon(car, door_1.getWidth(), door_1.getHeight() + 28));
            door_2.setIcon(resizeIcon(goat, door_2.getWidth(), door_2.getHeight() + 28));
            door_3.setIcon(resizeIcon(goat, door_3.getWidth(), door_3.getHeight() + 28));
        } else if (winningDoor == 2) {
            door_2.setIcon(resizeIcon(car, door_2.getWidth(), door_2.getHeight() + 28));
            door_1.setIcon(resizeIcon(goat, door_1.getWidth(), door_1.getHeight() + 28));
            door_3.setIcon(resizeIcon(goat, door_3.getWidth(), door_3.getHeight() + 28));
        } else if (winningDoor == 3) {
            door_3.setIcon(resizeIcon(car, door_3.getWidth(), door_3.getHeight() + 28));
            door_1.setIcon(resizeIcon(goat, door_1.getWidth(), door_1.getHeight() + 28));
            door_2.setIcon(resizeIcon(goat, door_2.getWidth(), door_2.getHeight() + 28));
        }
    }
    // check if the selected door is the winning door
    private void checkWin() {
        totalGames++;
        if (isSwitched) {
            if (selectedDoor == winningDoor) {
                winsSwitch++;
                JOptionPane.showMessageDialog(this, "Congratulations! You switched and you win", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                lossesSwitch++;
                JOptionPane.showMessageDialog(this, "You switched and you lose", "Result", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else if (!isSwitched) {
            if (selectedDoor == winningDoor) {
                winsStay++;
                JOptionPane.showMessageDialog(this, "Congratulations! You stayed and you win", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                lossesStay++;
                JOptionPane.showMessageDialog(this, "You stayed and you lose", "Result", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        winLabel.setText("<html><body>"
        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total games: " + totalGames + "<br>"
        + "Change choice&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Wins: " + winsSwitch + " | Losses: " + lossesSwitch + "<br>"
        + "Stay choice&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Wins: " + winsStay + " | Losses: " + lossesStay
        + "</body></html>");
    }
    private JButton createDoorButton(int index) {
        JButton button = new JButton("Door " + (index + 1));
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setIcon(door);

        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                button.setIcon(resizeIcon(door, button.getWidth(), button.getHeight() + 28));
            }
        });

        button.setEnabled(true);
        return button;
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    // Function to randomly determine the winning door
    private int getWinningDoor() {
        Random rand = new Random();
        int winningDoor = rand.nextInt(3) + 1 ;
        System.out.println("Winning door: " + winningDoor);
        return winningDoor;
    }

    // Function to determine the remaining doors
    private int[] getRemainingDoors(int winningDoor) {
        int index = 0;
        for (int i = 1; i <= 3; i++) {
            if (i != winningDoor) {
                remainingDoors[index] = i;
                index++;
            }
        }
        System.out.println("Remaining doors: " + remainingDoors[0] + " " + remainingDoors[1]);
        return remainingDoors;
    }
    
    public static void main(String[] args) {
        new Monty_Hall().setVisible(true);
    }
}

