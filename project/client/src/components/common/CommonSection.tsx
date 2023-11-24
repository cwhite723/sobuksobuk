import { Box } from "@mui/material";

interface PropsType {
  children: React.ReactNode;
  maxHight?: number;
}

const CommonSection: React.FC<PropsType> = (props) => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "start",
        maxHeight: props.maxHight ? props.maxHight : "100%",
        overflowY: "auto",
        "::-webkit-scrollbar": {
          display: "none",
        },
        backgroundColor: "primary.main",
        boxShadow: "0px 0px 5px rgba(0,0,0,0.5)",
        borderRadius: 5,
        mt: { xs: 4, md: 6 },
        p: 4,
      }}
    >
      {props.children}
    </Box>
  );
};
export default CommonSection;