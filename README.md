# MemorySimulation
## super outdated stuff
The purpose of this project is to simulate the essence of how memory works. As for functions such as read-only or random access, those have yet to be implemented. 
As of now, only initialization, modules, and interfaces have been developed. A simple command line interface is currently in use.
### Commands
**help - shows commands**\
**chmem <offset: 00000000> - changes memory at specified address**\
**dispmem - shows contents of memory**\
**flush - fills mem with all zeros**\
**reinit <size> - reinitialize new memory with new size.**\
**multich <addr1,addr2,addr3...> - change multiple addresses with one command**\
**exit - exits the program**\
**checksum - prints the sha256 checksum of bytes**\
**verbose - turns on additional information**\
**mute - toggle off verbosity**\
**ejectdisp - eject memory from the display**\
**ejectint - eject memory from the interactor**\
**loaddisp - load current virtual memory instance into display**\
**loadint - load current virtual memory instance into interactor**\
**newmem <size>- replaces the instance of memory with a new one**

put any folders to be read into the root dir of where the jvm was invoked. im too lazy to add a FileDialog implementation.

theres stuff like loadmem that isnt documented and theres also gonna be a new way to edit bytes.
its gonna be a bootleg HxD clone one day lmao.

Still no gui? yep feelsbadman

>Developed by Yi Cheng Quan