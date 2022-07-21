# MemorySimulation
The purpose of this project is to simulate the essence of how memory works. As for functions such as read-only or random access, those have yet to be implemented. 
As of now, only initialization, modulization, and interfaces have been developed. A simple command line interface is currently in use.
### Commands
**help - shows commands**\
**chmem <offset: 00000000> - changes memory at specified address**\
**dispmem - shows contents of memory**\
**flush - fills mem with all zeros**\
**reinit <size> - reinitialize new memory with new size.**\
**multich <addr1,addr2,addr3...> - change multiple addresses with one command**\
**exit - exits the program**\
**checksum - prints the sha256 checksum of memory**\
**verbose - turns on additional information**\
**mute - toggle off verbosity**\
**ejectdisp - eject memory from the display**\
**ejectint - eject memory from the interactor**\
**loaddisp - load current virtual memory instance into display**\
**loadint - load current virtual memory instance into interactor**\
**newmem <size>- replaces the instance of memory with a new one**

>Developed by Yi Cheng Quan