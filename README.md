# Regex Engine
Simple Regex engine written in Java.

## Phases
1. Parse AST from regex string.
2. Construct NFA.
3. Transform NFA into DFA utilizing Thompson's construction.
4. Step through DFA char by char.

## Supported regex features
- `r?`: Optional
- `r+`: Repetition (at least once)
- `r*`: Repetition
- `(r)`: Grouping
- `rr`: Conjunction
- `r|r`: Disjunction

## Examples
- `a(b|c)+de`
- `ab?`
- `a*b(cd)+`

## Usage
`java -jar regex-engine.jar <pattern> <input>`


## GraphViz
When executed directly through CLI, [Graphviz](https://graphviz.org/) DOT code will be emitted. 
It can be used to visualize the automatons generated from the provided regex (for example using 
[this website](http://magjac.com/graphviz-visual-editor/)).
### Example
#### Regex
`(a|b)*`

#### NFA
```graphviz
digraph nfa_regex {
  node [shape=doublecircle, style=solid]; 10;
  node [shape=point, style=invis]; ENTRY;
  node [shape=circle, style=solid];
  ENTRY -> 0;
  0 -> 1 [label=<&epsilon;>]
  1 -> 2 [label=<&epsilon;>]
  2 -> 9 [label=<&epsilon;>]
  9 -> 10 [label=<&epsilon;>]
  9 -> 2 [label=<&epsilon;>]
  2 -> 3 [label=<&epsilon;>]
  3 -> 6 [label=<&epsilon;>]
  6 -> 7 [label=b]
  7 -> 8 [label=<&epsilon;>]
  8 -> 9 [label=<&epsilon;>]
  3 -> 4 [label=<&epsilon;>]
  4 -> 5 [label=a]
  5 -> 8 [label=<&epsilon;>]
}
```
![NFA](assets/examples/nfa.svg)

#### DFA
```graphviz
digraph dfa_regex {
  node [shape=doublecircle, style=solid]; 13 11 12;
  node [shape=point, style=invis]; ENTRY;
  node [shape=circle, style=solid];
  ENTRY -> 11;
  11 -> 12 [label=a]
  12 -> 12 [label=a]
  12 -> 13 [label=b]
  13 -> 12 [label=a]
  13 -> 13 [label=b]
  11 -> 13 [label=b]
}
```
![NFA](assets/examples/dfa.svg)