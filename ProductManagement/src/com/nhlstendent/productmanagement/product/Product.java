package com.nhlstendent.productmanagement.product;

import java.util.Objects;

public class Product
{
    private String name;
    private double price;
    private double rating;

    public Product()
    {
    }

    public Product(String name, double price, double rating)
    {
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
    {
        this.rating = rating;
    }

    @Override
    public String toString()
    {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(name);
    }
}
