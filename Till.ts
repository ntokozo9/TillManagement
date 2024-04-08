interface Item {
  description: string;
  amount: number;
}

interface Transaction {
  items: Item[];
  paid: string;
}

interface Change {
  total: number;
  breakdown: string;
}

function calculateChange(till: number, transaction: Transaction): Change {
  const total = transaction.items.reduce((sum, item) => sum + item.amount, 0);
  const paid = parseInt(transaction.paid.replace(/R/g, ''));
  const changeTotal = paid - total;
  let tillRemaining = till;
  const changeBreakdown: string[] = [];

  const denominations = [100, 50, 20, 10, 5, 2, 1];

  for (const denomination of denominations) {
    const count = Math.floor(tillRemaining / denomination);
    if (count > 0) {
      changeBreakdown.push(`R${denomination}-`.repeat(count).slice(0, -1));
      tillRemaining -= denomination * count;
    }
  }

  return {
    total: changeTotal,
    breakdown: changeBreakdown.join('-'),
  };
}

function processTransactions(till: number, transactions: Transaction[]): string {
  let tillRemaining = till;
  const output: string[] = [];

  for (const transaction of transactions) {
    const change = calculateChange(tillRemaining, transaction);
    output.push(`Till Start, ${tillRemaining}, Transaction Total, ${transaction.items.reduce((sum, item) => sum + item.amount, 0)}, Paid, ${transaction.paid}, Change Total, ${change.total}, Change Breakdown, ${change.breakdown}`);
    tillRemaining += change.total;
  }

  output.push(`Till End, ${tillRemaining}`);

  return output.join('\n');
}

const transactions: Transaction[] = [
  {
    items: [
      { description: 'Fresh full cream milk', amount: 30 },
      { description: 'Free range large eggs', amount: 24 },
    ],
    paid: 'R20-R20-R20',
  },
  {
    items: [
      { description: 'Double cream plain yoghurt', amount: 38 },
      { description: 'Cheddar', amount: 42 },
      { description: 'Kiri cheese spread', amount: 33 },
    ],
    paid: 'R100-R100',
  },
  //... add more transactions here
];

console.log(processTransactions(500, transactions));