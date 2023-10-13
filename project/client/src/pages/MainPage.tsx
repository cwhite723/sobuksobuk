import { Box } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2";
import CommonButton from "components/common/CommonButton";
import MainSection from "components/main/MainSection";
import CommonTypography from "components/common/CommonTypography";
import MainBookProgressCard from "components/main/MainBookProgressCard";
import MainBookRankCard from "components/main/MainBookRankCard";

const MainPage = () => {
  return (
    <Box>
      {/* 독서진행률 */}
      <MainSection>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "end",
            mb: 2,
          }}
        >
          <CommonTypography
            value="📚 완독까지 이만큼 남았어요"
            bold={true}
            variant="h5"
          />
          <CommonButton value="책 추가하기" />
        </Box>
        <MainBookProgressCard />
        <MainBookProgressCard />
        <MainBookProgressCard />
        <MainBookProgressCard />
        <MainBookProgressCard />
      </MainSection>

      {/* 인기도서 */}
      <MainSection>
        <Box
          sx={{
            display: "flex",
            alignItems: "end",
            mb: 2,
          }}
        >
          <CommonTypography
            value="📚 인기도서 TOP10"
            bold={true}
            variant="h5"
          />
        </Box>

        {/* 도서container */}
        <Grid container spacing={2} columns={{ xs: 1, md: 10 }}>
          {/* 도서item */}
          <MainBookRankCard />
          <MainBookRankCard />
          <MainBookRankCard />
          <MainBookRankCard />
          <MainBookRankCard />
          <MainBookRankCard />
          <MainBookRankCard />
          <MainBookRankCard />
          <MainBookRankCard />
          <MainBookRankCard />
        </Grid>
      </MainSection>
    </Box>
  );
};

export default MainPage;