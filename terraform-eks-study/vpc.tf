resource "aws_vpc" "terraform-eks-vpc" {
  cidr_block = "10.110.0.0/16"

  tags = {
    "Name"                                      = "terraform-eks-node"
    "kubernetes.io/cluster/${var.cluster-name}" = "shared"
  }
}

# public subnet
resource "aws_subnet" "terraform-eks-public-subnet" {
  count = 2

  availability_zone       = data.aws_availability_zones.available.names[count.index]
  cidr_block              = "10.110.${count.index + 1}.0/24"
  map_public_ip_on_launch = true
  vpc_id                  = aws_vpc.terraform-eks-vpc.id

  tags = {
    "Name"                                      = "terraform-eks-public-${count.index + 1 == 1 ? "a" : "c"}"
    "kubernetes.io/cluster/${var.cluster-name}" = "shared"
  }
}

# igw : 외부와 인터넷 통신이 가능하게 하는 게이트웨이
resource "aws_internet_gateway" "terraform-eks-igw" {
  vpc_id = aws_vpc.terraform-eks-vpc.id

  tags = {
    Name = "terraform-eks-igw"
  }
}

# public route table
resource "aws_route_table" "terraform-eks-public-route" {
  vpc_id = aws_vpc.terraform-eks-vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.terraform-eks-igw.id
  }

  tags = {
    "Name" = "terraform-eks-public"
  }
}

# create route table association
# Subnet 과 Route table 을 연결
resource "aws_route_table_association" "terraform-eks-public-routing" {
  count = 2

  subnet_id      = aws_subnet.terraform-eks-public-subnet.*.id[count.index]
  route_table_id = aws_route_table.terraform-eks-public-route.id
}
