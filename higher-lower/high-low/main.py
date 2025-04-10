import random
import math

def guess(x):
    r_number = random.randint(1, x)
    user_number = 0
    while r_number != user_number:
        user_number = int(input('What is ur guess? '))
        if user_number == r_number:
            break
        if r_number > user_number:
            print('My number is higher!')
        if r_number < user_number:
            print('My number is lower!')
    print('Correct!')

def computer_guess(x):
    my_guess=int(x/2)
    Help = x+1
    help = 0
    attemps = 0
    while True:
        attemps+=1
        anwser = input(f'is the number you think about {my_guess} (type h, l or y) ')
        if attemps > math.log2(x):
            print('You lied to me at least one time!')
            return
        if anwser == 'y':
            break
        elif anwser == 'h':
            help = my_guess
            my_guess += int((Help-my_guess)/2)
        elif anwser == 'l':
            Help = my_guess
            my_guess -= int((my_guess-help)/2)
        else:
            print('no such input')
    print ('I knew it!')
computer_guess(100)