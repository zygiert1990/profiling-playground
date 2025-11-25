## Quick start

1. Build project with `mvn clean install` and run with: `java -jar target/app.jar 20000 100`
2. Attach `jhsdb` command line debugger to running JVM: `jhsdb clhsdb --pid <pid>`
3. Find pointer to Main class: `class org.zygiert.Main`:
```
org/zygiert/Main @0x000000004b040000
```
4. Inspect class metadata details: `inspect 0x000000004b040000`:
```
Type is InstanceKlass (size of 472)
juint Klass::_super_check_offset: 56
Klass* Klass::_secondary_super_cache: Klass @ null
Array<Klass*>* Klass::_secondary_supers: Array<Klass*> @ 0x000000004a6783c0
Klass* Klass::_primary_supers[0]: Klass @ 0x000000004a171bf8
OopHandle Klass::_java_mirror: OopHandle @ 0x000000004b040070
Klass* Klass::_super: Klass @ 0x000000004a171bf8
Klass* Klass::_subklass: Klass @ null
jint Klass::_layout_helper: 16
Symbol* Klass::_name: Symbol @ 0x00007f67c40e5718
AccessFlags Klass::_access_flags: 33
Klass* Klass::_next_sibling: Klass @ 0x000000004a24ef70
Klass* Klass::_next_link: Klass @ null
int Klass::_vtable_len: 5
ClassLoaderData* Klass::_class_loader_data: ClassLoaderData @ 0x00007f67c40d7a30
ObjArrayKlass* InstanceKlass::_array_klasses: ObjArrayKlass @ null
Array<Method*>* InstanceKlass::_methods: Array<Method*> @ 0x00007f678c400348
Array<Method*>* InstanceKlass::_default_methods: Array<Method*> @ null
Array<InstanceKlass*>* InstanceKlass::_local_interfaces: Array<InstanceKlass*> @ 0x000000004a6783d8
Array<InstanceKlass*>* InstanceKlass::_transitive_interfaces: Array<InstanceKlass*> @ 0x000000004a6783d8
Array<u1>* InstanceKlass::_fieldinfo_stream: Array<u1> @ 0x00007f678c400650
ConstantPool* InstanceKlass::_constants: ConstantPool @ 0x00007f678c400058
char* InstanceKlass::_source_debug_extension: char @ null
Array<jushort>* InstanceKlass::_inner_classes: Array<jushort> @ 0x00007f678c400638
Array<jushort>* InstanceKlass::_nest_members: Array<jushort> @ 0x000000004a6783d0
int InstanceKlass::_nonstatic_field_size: 0
int InstanceKlass::_static_field_size: 0
u2 InstanceKlass::_static_oop_field_count: 0
int InstanceKlass::_nonstatic_oop_map_size: 0
InstanceKlass::ClassState InstanceKlass::_init_state: 4
JavaThread* InstanceKlass::_init_thread: JavaThread @ null
int InstanceKlass::_itable_len: 2
u2 InstanceKlass::_nest_host_index: 0
u1 InstanceKlass::_reference_type: 0
OopMapCache* InstanceKlass::_oop_map_cache: OopMapCache @ null
JNIid* InstanceKlass::_jni_ids: JNIid @ null
nmethod* InstanceKlass::_osr_nmethods_head: nmethod @ null
BreakpointInfo* InstanceKlass::_breakpoints: BreakpointInfo @ null
jmethodID* InstanceKlass::_methods_jmethod_ids: jmethodID @ 0x00007f67c40f9ff0
u2 InstanceKlass::_idnum_allocated_count: 3
Annotations* InstanceKlass::_annotations: Annotations @ null
Array<int>* InstanceKlass::_method_ordering: Array<int> @ 0x000000004a6f8e30
Array<int>* InstanceKlass::_default_vtable_indices: Array<int> @ null
```
5. Inspect methods metadata pointers: `mem 0x00007f678c400348/4` (since I know that there are 3 methods):
```
0x00007f678c400348: 0x0000000000000003
0x00007f678c400350: 0x00007f678c4003b8
0x00007f678c400358: 0x00007f678c4004d8
0x00007f678c400360: 0x00007f678c4005b0
```
6. Inspect specific method metadata: `inspect 0x00007f678c4005b0`:
```
Type is Method (size of 88)
ConstMethod* Method::_constMethod: ConstMethod @ 0x00007f678c400550
MethodData* Method::_method_data: MethodData @ 0x00007f678c4007f8
MethodCounters* Method::_method_counters: MethodCounters @ 0x00007f678c4007b8
AccessFlags Method::_access_flags: 10
int Method::_vtable_index: -2
u2 Method::_intrinsic_id: 0
nmethod* Method::_code: nmethod @ 0x00007f67ac427288
address Method::_i2i_entry: address @ 0x00007f678c4005e8
address Method::_from_compiled_entry: address @ 0x00007f678c4005f0
address Method::_from_interpreted_entry: address @ 0x00007f678c400600
```
7. Inspect method counters: `inspect 0x00007f678c4007b8`:
```
Type is MethodCounters (size of 64)
int MethodCounters::_invoke_mask: 254
int MethodCounters::_backedge_mask: 2046
u2 MethodCounters::_interpreter_throwout_count: 0
u2 MethodCounters::_number_of_breakpoints: 0
InvocationCounter MethodCounters::_invocation_counter: 512
InvocationCounter MethodCounters::_backedge_counter: 0
```
8. Inspect method data: `inspect 0x00007f678c4007f8`:
```
Type is MethodData (size of 216)
int MethodData::_size: 280
Method* MethodData::_method: Method @ 0x00007f678c4005b0
int MethodData::_data_size: 32
intptr_t MethodData::_data[0]: 262151
int MethodData::_parameters_type_data_di: -2
uint MethodData::_compiler_counters._nof_decompiles: 0
uint MethodData::_compiler_counters._nof_overflow_recompiles: 0
uint MethodData::_compiler_counters._nof_overflow_traps: 0
u1 MethodData::_compiler_counters._trap_hist._array[0]: 0
intx MethodData::_eflags: 0
intx MethodData::_arg_local: 0
intx MethodData::_arg_stack: 0
intx MethodData::_arg_returned: 0
uint MethodData::_tenure_traps: 0
int MethodData::_invoke_mask: 254
int MethodData::_backedge_mask: 2046
```
9. Inspect `invocation_counter` in `MethodData` - from `MethodData` structure we know that `invocation_counter`
resides at 144 byte offset from `MethodData` start: `printas InvocationCounter 0x00007f678c400888`:
```
pointer to unsigned InvocationCounter @ 0x00007f678c400888 (size = 4)
unsigned int InvocationCounter::_counter: 68
```
10. Inspect `backedge_counter` in `MethodData` - from `MethodData` structure we know that `backedge_counter`
    resides at 152 byte offset from `MethodData` start: `printas InvocationCounter 0x00007f678c400890`:
```
pointer to unsigned InvocationCounter @ 0x00007f678c400890 (size = 4)
unsigned int InvocationCounter::_counter: 0
```
11. Disassemble machine code: `disassemble 0x00007f67ac427288` - for this `hsdis` binary is required. Can be either
downloaded from [here](https://chriswhocodes.com/hsdis/) or build from [sources](https://github.com/liuzhengyang/hsdis).