
export default function Pending(){
  return (
    <div className="flex flex-col drop-shadow-lg p-5 justify-between bg-white w-4/5 md:w-2/6 rounded-md" >
      <p className="text-sm w-15 mb-5 font-semibold">Uploading...</p>
      <div className="w-full p-0 h-1 bg-grey mb-5 relative">
        <div className="absolute w-0 loader bg-blue h-1"/>
      </div>
    </div>
  )
}