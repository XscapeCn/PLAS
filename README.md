# PLAS: GPU-based de novo assembler

![GitHub](https://img.shields.io/github/license/XscapeCn/PLAS)



## Rationale





## Limitations





## Installation

1. Clone the repository and run:

   ```Shell
   $ git clone https://github.com/XscapeCn/PLAS
   $ cd PLAS
   $ ./build.sh
   ```

Make sure that you have a working GPU  environment. See more details in GPU section.



## GPU related

The software is GPU-based. So before using, you should make sure that you have a NVIDIA GPU (for using CUDA) and a GPU environment. These are what you need:

NVIDIA CUDA toolkits, the guide link is as [below](https://docs.nvidia.com/cuda/cuda-installation-guide-linux/index.html).



## Quick start

```Shell
$ ./build.sh
$ plas example/example.fasta example/result
$ gfatools asm -u /example/result.gfa
```



## Overview

`PLAS` is a de novo assembler based on 3 modules, which are:

1. All-to-all alignment, this module is a revision of [WFA-GPU](https://github.com/quim0/WFA-GPU), a GPU implementation of the WFA for sequence alignment.
2. Graph build, build a string graph according to the results of 1st step.
3. Transitive reduction, also called "Bubble removal", is based on Floyd Warshall algorithm, and can be migrated to NVIDIA GPU.

## Input

`PLAS` takes a single FASTA/FASTQ input (gzip-compressed or not). 

See more example data in Example.

## Output



## Example



## Parameter



## Performance



## License

`PLAS` is freely available under [MIT License](https://opensource.org/licenses/MIT).



## Developer

Song Xu, supervised by Fei Lu at the Institution of Genetics and Developmental Biology([IGDB](http://www.genetics.cas.cn/)), Chinese Academy of Sciences(CAS).



## Citation
