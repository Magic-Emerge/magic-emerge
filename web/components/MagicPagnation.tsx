import { IconChevronLeft, IconChevronRight } from "@tabler/icons-react";
import { FC } from "react";

interface PaginationProps {
  totalPages: number | undefined;
  currentPage: number | undefined;
  totalSize: number | undefined;
  onPageChange: (page: number) => void;
}

const MagicPagination: FC<PaginationProps> = ({
  totalPages,
  currentPage,
  totalSize,
  onPageChange,
}) => {
  const pages = [...Array(totalPages).keys()].map((num) => num + 1);

  return (
    <div className="flex justify-end my-2 space-x-2 w-full">
      <p className="flex py-1 px-2 font-sans text-sm font-normal text-gray-primary">
        共{<span className="mx-1">{totalSize}</span>}条
      </p>
      {currentPage && (
        <button
          disabled={currentPage === 1}
          onClick={() => onPageChange(currentPage - 1)}
          className={`px-2 py-1 h-8 w-8 text-gray-primary font-sans text-sm font-normal bg-white rounded  ${
            currentPage === 1
              ? "cursor-not-allowed opacity-50"
              : "hover:text-gray-400"
          }`}
        >
          <IconChevronLeft />
        </button>
      )}

      {pages.map((page) => (
        <button
          key={page}
          onClick={() => onPageChange(page)}
          className={`px-2 py-1 h-8 w-8 bg-white font-sans text-sm font-normal hover:bg-slate-100 rounded-lg ${
            currentPage === page
              ? "text-blue-select border border-blue-primary rounded-lg hover:bg-white"
              : "text-gray-primary"
          }`}
        >
          {page}
        </button>
      ))}

      {currentPage && totalPages && (
        <button
          disabled={currentPage === totalPages}
          onClick={() => onPageChange(currentPage + 1)}
          className={`px-2 py-1 h-8 w-8 text-gray-primary font-sans text-sm font-normal bg-white rounded  ${
            currentPage === totalPages
              ? "cursor-not-allowed opacity-50"
              : "hover:text-gray-400"
          }`}
        >
          <IconChevronRight />
        </button>
      )}
    </div>
  );
};

export default MagicPagination;
