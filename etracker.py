import json
import os
from datetime import datetime
import matplotlib.pyplot as plt

FILE_NAME = "expenses.json"

def load_exp():
    if not os.path.exists(FILE_NAME):
        return []
    with open(FILE_NAME, "r") as file:
        return json.load(file)


def save_exp(expenses):
    with open(FILE_NAME, "w") as file:
        json.dump(expenses, file, indent=4)


def add_exp():
    date = input("Enter date (YYYY-MM-DD): ")
    category = input("Enter category: ")
    amount = float(input("Enter amount: "))

    expense = {
        "date": date,
        "category": category,
        "amount": amount,
    }

    expenses = load_exp()
    expenses.append(expense)
    save_exp(expenses)

    print("Expense added\n")


def view_exp():
    expenses = load_exp()
    if not expenses:
        print("No expenses found.\n")
        return

    print("\n All Expenses ")
    for exp in expenses:
        print(f"{exp['date']} | {exp['category']} | Rs{exp['amount']} ")
    print()


def monthly_summary():
    expenses = load_exp()
    month = input("Enter month (YYYY-MM): ")

    total = 0
    for exp in expenses:
        if exp["date"].startswith(month):
            total += exp["amount"]

    print(f"\nTotal expenses for {month}: ₹{total}\n")

def category_breakdown():
    expenses = load_exp()
    categories = {}

    for exp in expenses:
        cat = exp["category"]
        categories[cat] = categories.get(cat, 0) + exp["amount"]

    if not categories:
        print("No data available.")
        return

    print("\n Category Breakdown ")
    for cat, amt in categories.items():
        print(f"{cat}: ₹{amt}")


    plt.pie(categories.values(), labels=categories.keys(), autopct='%1.1f%%')
    plt.title("Expense Distribution")
    plt.show()



def highest_spending():
    expenses = load_exp()
    categories = {}

    for exp in expenses:
        cat = exp["category"]
        categories[cat] = categories.get(cat, 0) + exp["amount"]

    if not categories:
        print("No data available.\n")
        return

    highest = max(categories, key=categories.get)
    print(f"\n Highest spending category: {highest} ({categories[highest]})\n")


def menu():
    while True:
        print("Smart Expense Tracker")
        print("1. Add Expense")
        print("2. View Expenses")
        print("3. Monthly Summary")
        print("4. Category Breakdown")
        print("5. Highest Spending Category")
        print("6. Exit")

        choice = input("Enter choice: ")

        if choice == "1":
            add_exp()
        elif choice == "2":
            view_exp()
        elif choice == "3":
            monthly_summary()
        elif choice == "4":
            category_breakdown()
        elif choice == "5":
            highest_spending()
        elif choice == "6":
            print("Thankyou")
            break
        else:
            print("Invalid\n")


if __name__ == "__main__":
    menu()