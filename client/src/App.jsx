import { Button, HStack , Link, Text } from "@chakra-ui/react";
import { Toaster, toaster } from "@/components/ui/toaster"


function App() {
  
  return (
    <>
      <Toaster />
      <HStack>
        <Button  onClick={()=>toaster.create({title: 'hello wasfa!',type:'success'})} >Click me</Button>
        <Button>Click me</Button>
      </HStack>
    </>
  );
}

export default App;
