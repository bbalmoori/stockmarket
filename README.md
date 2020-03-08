# Simple stock application

This is a simple stock market application which performs the following actions

Requirements
1. Provide working source code that will:-
    a. For a given stock,
        i. Given any price as input, calculate the dividend yield
        ii. Given any price as input, calculate the P/E Ratio
        iii. Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price
        iv. Calculate Volume Weighted Stock Price based on trades in past 15 minutes
    b. Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

## Setup

Building this project requires maven. All you need to do is import project as maven in IDE by pointing it to pom.xml.

## Dependencies

No prior dependencies are required


## Assumptions

This project does not require a GUI or persistence layer. All the trade/instrument will be store in memory.

##Improvements

Need to convert this project as SpringBoot and Rest application to fully operate as Rest service