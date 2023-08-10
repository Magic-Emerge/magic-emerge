import Image from "next/image";

export default function Uploaded({ image, url }) {
  return (
    <div className="flex flex-col drop-shadow-lg p-5 justify-between bg-white rounded-md">
      <div>
        <Image
          alt="logo"
          width={200}
          height={200}
          src={image}
          className="max-w-full mx-auto mt-4 rounded-md"
        />
      </div>
    </div>
  );
}
