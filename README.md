# Regex Engine
Simple Regex engine written in Java.

## Phases
1. Parse AST from regex string.
2. Construct NFA.
3. Transform NFA into DFA utilizing Thompson's construction.
4. Step through NFA char by char.

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
