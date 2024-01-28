# l2-brush-export

## What is it ?

Tool to enhance [Acmi's L2pe tool](https://github.com/acmi/L2pe) to export brush data from Lineage2 to JSON.

## How to use ?

Execute the l2-ambientsound-export-x.x.x.jar with the given parameters:
- Lineage2 game folder path
- Brush map ID
- [Optional] Specific ambient sound name(s) (separate with ,)

## Example
```
$ java -jar l2-ambientsound-export-1.0.0.jar "D:\Games\Lineage II" 17_25 Brush54
```
## Why ?
I made this tool to import ambient sounds into the other project [l2-unity](https://gitlab.com/shnok/l2-unity).
