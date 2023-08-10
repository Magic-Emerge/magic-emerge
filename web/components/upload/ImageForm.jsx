import { useCallback, useState } from "react";
import { useDropzone } from "react-dropzone";

import Bgimage from "../../assets/images/image.svg";
import Image from "next/image";

export default function ImageForm({
  image,
  setImage,
  isPending,
  setIsPending,
  url,
  setUrl,
  token,
  workspaceId,
}) {
  const uploadImage = useCallback(
    async (image) => {
      setIsPending(true);
      const formData = new FormData();
      console.log('images', image)
      formData.append("image", image);
      try {
        const res = await fetch("/api/v1/file/image/upload", {
          method: "POST",
          body: formData,
          'content-type': 'multipart/form-data',
          headers: {
            "Authorization": `Bearer ${token}`,
            "workspaceId": workspaceId,
          }
        });
        if (!res.ok) {
          throw Error("Internal Server Error");
        }
        const data = await res.json();
        setUrl(data.data);
        setIsPending(false);
      } catch (error) {
        console.log(error);
        setIsPending(false);
      }
    },
    [setIsPending, setUrl, token, workspaceId]
  );

  const onDrop = useCallback(
    async (acceptedFiles) => {
      let file = acceptedFiles[0];
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        setImage(reader.result);
        uploadImage(file);
      };
    },
    [setImage, uploadImage]
  );

  const { getRootProps, getInputProps, open } = useDropzone({
    onDrop,
    maxFiles: 1,
    accept: { "image/*": [] },
    noClick: true,
    noKeyboard: true,
  });

  return (
    <div className="flex flex-col my-2 drop-shadow-lg p-5 justify-between bg-white w-full rounded-md">
      <p className="text-center font-semibold text-lg md:text-xl mb-2">
        Upload your image
      </p>
      <p className="text-center font-thin text-xs text-slate-400 mb-2">
        File should be Jpeg , Png...
      </p>
      <div
        {...getRootProps({
          className:
            "md:h-52 sm:h-44 h-auto bg-light-grey border-2 border-light-blue border-dashed rounded-md",
        })}
      >
        <input {...getInputProps({ name: "image" })} />
        <Bgimage className="mx-auto my-10"/>
        <p className="text-slate-400 md:text-md text-center mt-4 text-sm">
          Drag & Drop your image here
        </p>
      </div>
      <p className="text-center font-normal text-slate-400 text-md mt-2 mb-2">
        Or
      </p>
      <button
        type="button"
        onClick={open}
        className="bg-blue-primary text-white font-normal rounded-lg mx-auto px-4 py-2 text-md"
      >
        Choose a file
      </button>
    </div>
  );
}
