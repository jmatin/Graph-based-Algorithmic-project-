
## Project Overview

This project was developed for the course **Algorithmique 2** at the **Université libre de Bruxelles (ULB)**.

The project studies a combinatorial lighting problem defined on a square grid of horizontal and vertical cables, where light bulbs are connected at specific intersections. Each bulb is controlled by two switches (row and column), and its activation depends on predefined combinations of switch positions.

---

## Objectives

The project is divided into two main parts:

### 1. Decision Problem
Determine whether **it is possible to turn on all bulbs simultaneously** by finding a configuration of row and column switches that satisfies all bulb constraints.

This part includes:
- Designing an **efficient algorithm** based on logical implications.
- Modeling the problem using a **directed implication graph**.
- Analyzing the **time and space complexity**.
- Comparing the algorithm’s complexity to known lower bounds.
- Providing a **Java implementation** of the solution.

### 2. Optimization Problem
Compute the **maximum number of bulbs that can be turned on simultaneously**, generalizing the decision problem.

This part requires:
- A detailed algorithmic design and analysis.
- A Java implementation.
- Handling the same input format as the decision problem.

---

## Key Concepts

- Graph-based modeling using **implication graphs**
- Boolean constraints and satisfiability reasoning
- Algorithmic complexity analysis
- Efficient Java implementation

---

## Input Format

Each problem instance is provided as a text file with one line per bulb, specifying:
- The bulb’s position (row, column)
- Four binary values indicating whether the bulb is on for each combination of switch states

---

## Deliverables

- A **scientific report** (written in LaTeX, PDF format)
- A **Java program and a python program** implementing the algorithms
-
---
