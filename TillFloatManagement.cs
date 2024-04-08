using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace TillFloatManagement
{
    class Program
    {
        static void Main(string[] args)
        {
            string inputFilePath = "input.txt";
            string outputFilePath = "output.txt";

            List<string> inputLines = File.ReadAllLines(inputFilePath).ToList();
            List<string> outputLines = new List<string>();

            int tillStart = 500;
            outputLines.Add($"Till Start, Transaction Total, Paid, Change Total, Change Breakdown {tillStart}");

            foreach (string line in inputLines)
            {
                string[] parts = line.Split(',');
                string[] items = parts[0].Split(';');
                string[] paidParts = parts[1].Split('-');

                int transactionTotal = 0;
                int paid = int.Parse(paidParts[0]);
                int changeTotal = paid - transactionTotal;
                List<int> changeBreakdown = new List<int>();

                for (int i = 1; i < paidParts.Length; i++)
                {
                    changeBreakdown.Add(int.Parse(paidParts[i]));
                }

                while (changeTotal > 0)
                {
                    if (changeTotal >= 100 && tillStart >= 100)
                    {
                        changeBreakdown.Add(100);
                        tillStart -= 100;
                        changeTotal -= 100;
                    }
                    else if (changeTotal >= 50 && tillStart >= 50)
                    {
                        changeBreakdown.Add(50);
                        tillStart -= 50;
                        changeTotal -= 50;
                    }
                    else if (changeTotal >= 20 && tillStart >= 20)
                    {
                        changeBreakdown.Add(20);
                        tillStart -= 20;
                        changeTotal -= 20;
                    }
                    else if (changeTotal >= 10 && tillStart >= 10)
                    {
                        changeBreakdown.Add(10);
                        tillStart -= 10;
                        changeTotal -= 10;
                    }
                    else if (changeTotal >= 5 && tillStart >= 5)
                    {
                        changeBreakdown.Add(5);
                        tillStart -= 5;
                        changeTotal -= 5;
                    }
                    else if (changeTotal >= 2 && tillStart >= 2)
                    {
                        changeBreakdown.Add(2);
                        tillStart -= 2;
                        changeTotal -= 2;
                    }
                    else if (changeTotal >= 1 && tillStart >= 1)
                    {
                        changeBreakdown.Add(1);
                        tillStart -= 1;
                        changeTotal -= 1;
                    }
                }

                outputLines.Add($"{tillStart}, {transactionTotal}, {paid}, {changeTotal}, {string.Join("-", changeBreakdown)}");
            }

            File.WriteAllLines(outputFilePath, outputLines);
        }
    }
}

